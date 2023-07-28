#Metoda Simpson
import math
from sympy import *
from matplotlib import pyplot as plt
from matplotlib.animation import FuncAnimation, writers
from scipy.integrate import quadrature
import matplotlib.pyplot as plt
import numpy as np
from scipy.interpolate import lagrange
from scipy.integrate import quadrature

def simpson(f,a,b,n):
   '''
    
   aproximate definite integration using midpoint rule
    
   '''
   h = (b-a)/(6*n)
   x = np.linspace(a,b,n+1)
   # plotati functia
   x1 = np.linspace(a,b,100)
   plt.plot(x1,f(x1))
   int_approx = 0
   for i in range(n):
       #f_mid = f((x[i]+x[i+1])/2)
       f_mid = f(x[i])+4*f((x[i]+x[i+1])/2)+f(x[i+1])
       int_approx+=f_mid
       # xval = [x[i],x[i+1],x[i+1],x[i]]
       #yval = [0,0,f_mid, f_mid] dreptunghi
       #yval = [0,0,f(x[i+1]),f(x[i])] # trapez
       xi = [x[i],((x[i]+x[i+1])/2),x[i+1]]
       yi = f(xi)
       parabola = lagrange(xi,yi)
       xval = [x[i],x[i+1]]
       yval =[0,0]
       pct_intermed = np.linspace(x[i+1],x[i],10)
       y_pct_intermed = parabola(pct_intermed)
       #print(np.shape(y_pct_intermed))
       xval = np.hstack((xval,pct_intermed))
       yval = np.hstack((yval,y_pct_intermed))       
       plt.fill(xval,yval,color =(0.7,0.2,0.5,0.5))
   
   int_approx = h*int_approx
   plt.show()
   return int_approx
def roof(x):
    return np.sqrt(1+np.cos(x)**2)
int_approx = midpoint(roof,0,20,4)