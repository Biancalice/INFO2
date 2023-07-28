from numpy.linalg import solve, norm
import numpy as np
from time import perf_counter
import math

def eliminareGaussOptim(a):
 n = len(a)
 A = a.copy()
 L = np.eye(n)
 for j in range(n-1):
    for i in range(j+1,n):
        L[i][j]= a[i][j]/a[j][j]
        a[i][j] = 0
        a[i][j+1:] -= L[i][j]*a[j][j+1:]
 U = a.copy()
 print(L,U);
 print(np.allclose(L@U,A))
 return a,L

def substitutia_inainte(a,b):
   n = len(a)
   x = np.zeros(n);
   x[0] = b[0]/a[0][0]
   for i in range(1,n):
       x[i] = (b[i] - np.dot(a[i][:i],x[:i]))/a[i][i]
   return x


def substitutia_inapoi(a,b):
    n=len(a)
    x=np.zeros(n)
    x[n-1] = b[n-1]/a[n-1][n-1]
    for i in range(n-1,-1,-1):
        sum = 0
        for j in range(i+1,n):
            sum = sum + a[i][j] * x[j]
        x[i] = 1/a[i][i]*(b[i]-sum)
    return x

def descompunere_Choleski(a):
     n = len(a)
     A = a.copy()
     L = np.eye(n)
     L[0][0] = math.sqrt(a[0][0])
     for j in range(1,n):
         L[j][0] = a[j][0]/L[0][0]
     for i in range(1,n-1):
         sum1= 0
         for k in range(0,i):
             sum1 += L[i][k]**2  
         L[i][i] =(a[i][i]-sum1)**(1/2)
         for  j in range(i+1,n):
             sum1 = 0
             for k in range(0,i):
                 sum1 += L[j][k]*L[i][k]
             L[j][i] = (a[i][j] - sum1)/L[i][i]
     sum1=0
     for k in range(0,n-1):
        sum1 += L[n-1][k]**2
     L[n-1][n-1] = (a[n-1][n-1]- sum1)**(1/2)
     return L
         
a= [[1,2,3],[4,3,2],[-1,-2,-5]]
a = np.array(a)
a = a.astype(float)
b = [4,1,-4]
b= np.array(b).reshape((-1,1))
U,L= eliminareGaussOptim(a)
print(substitutia_inainte(a,b))
y = substitutia_inainte(L, b)
y_ex = solve(L,b).flatten()
print(norm(y_ex-y))
x = substitutia_inapoi(U,y)
x_ex = solve(U,y).flatten()
print(norm(x_ex - x))
a=[[1,0,1],[0,1,0],[1,0,1]]
print(descompunere_Choleski(a))
L = descompunere_Choleski(a)
print(np.allclose(L@L.T,a))

