# -*- coding: utf-8 -*-
"""
Created on Wed May  3 11:32:12 2023

@author: 40773
"""

import math
from sympy import *
from matplotlib import pyplot as plt
from matplotlib.animation import FuncAnimation,writers
import numpy as np 
metadata=dict(title='Lagrange01', artist='Matplotlib',comment='Lagrange Approximation Polynomials on [a,b]')
Writer=writers['ffmpeg']
writer=Writer(fps=1, metadata=metadata)
fig=plt.figure()

fs=input('f=')
x=Symbol('x')
f=lambdify(x,fs)

a=3; b=10
x_val=np.linspace(a,b,100)
t=(x_val-a)/(b-a)

ax=plt.axes()
ax.scatter(x_val,f(x_val))
           
nmax=20

def lagrange_frame(n):
    xi=np.linspace(a, b, n+1)
    ax.scatter(xi,f(xi))
    l=np.zeros_like(x_val)
    for j in range(1,n+1):
        prod=1
        for i in range(1,n+1):
            if i!=j:
                prod*=(x_val-xi[i])/(xi[j]-xi[i])
        l+=f(xi[j])*prod  
    ax.plot(x_val,l)
    plt.title('Degree'+str(n))
    
anim=FuncAnimation(fig, func=lagrange_frame,interval=10,frames=np.arange(1,nmax+1))
anim.save('Lagrange01.mp4',writer)     