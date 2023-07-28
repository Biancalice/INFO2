import math
from sympy import *
from matplotlib import pyplot as plt
from matplotlib.animation import FuncAnimation,writers
import numpy as np 
metadata=dict(title='Bernstein01', artist='Matplotlib',comment='Berstein Approximation Polynomials on [0,1]')
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

def bern_frame(n):
    bern=np.zeros_like(x_val)
    for k in range(n+1):
        bern+=f(k/n)*math.comb(n,k)*x_val**k*(1-x_val)**(n-k)
    ax.plot(x_val,bern)
    plt.title('Degree'+str(n))
    
anim=FuncAnimation(fig, func=bern_frame,interval=10,frames=np.arange(1,nmax+1))
anim.save('Bernstein01.mp4',writer) 