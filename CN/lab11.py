import numpy as np 
from numpy.linalg import solve, norm
from time import perf_counter

def eliminare_gauss_opt(a):  
      n=len(a)      
      for j in range(n-1):
        for i in range(j+1,n):
         m=a[i][j]/a[j][j]
         a[i][j]=0
         a[i,j+1:]-=m*a[j,j+1:]
      return a 


def eliminare_gauss(a):
    n = len(a)
    for j in range(1,n-1):
      for i in range(j+1,n):
       m=a[i][j]/a[j][j]
       a[i][j]=0
       for k in range(j+1,n+1):
           a[i][k]=a[i][k]-m*a[j][k]
    return a

def substitutie_inapoi(a,b,x):
    
    for i in range(n-1,1):
        sum=0
        for j in range(i+1,n):
            sum=sum+a[i][j]*x[j]
        x[i]=(1/a[i][i])*(b[i]-sum)
    return x
    
a=[[1,2,3],[4,3,2],[-1,-2,-5]]
a=np.array(a)
b=[4,1,-4]; b=np.array(b).reshape((-1,1))
n=len(a)
ae=np.hstack((a,b))
ae=ae.astype(float)
np.random.seed(1)
a2=np.random.randint(-5,5,(10,10))
b2=np.random.randint(-5,5,(10,1))
ae2=np.hstack((a2,b2))
t0=perf_counter()
aeReturnat = eliminare_gauss_opt(ae)
aeReturnat2=eliminare_gauss(ae)
t1=perf_counter()
durata=t1-t0
print(durata)

a_si=ae[:,:n]
b_si=ae[:,n]
x=np.zeros(n)
xReturnat=substitutie_inapoi(a_si,b_si,x)
x_ex=solve(a,b)
print(xReturnat,x,x_ex,norm(xReturnat-x_ex))
