from sympy import Symbol, diff, lambdify
import numpy as np
from scipy.optimize import fsolve
from time import perf_counter

def coarda_er(f,a,b,er):
    x=Symbol('x')
    f2=diff(f,x,2)
    f=lambdify(x,f); f2=lambdify(x,f2)
    r_ex=fsolve(f, (a+b)/2)
    if f(a)*f2(a)<0:
        x=a
        while abs(r_ex-x)>er:
            x=x-f(x)/(f(x)-f(b))*(x-b)
    else:
        if f(b)*f2(b)<0:
            x=b
            while abs(r_ex-x)>er:
                x=x-f(x)/(f(x)-f(a))*(x-a)
    return x

fs='sin(x)'
a=2
b=4
t0_t=perf_counter()
r_aprox=coarda_er(fs,a,b,10**-3)
print(r_aprox)         
t1_t=perf_counter()        
t_coarda=t1_t-t0_t

pop=lambda x: 1000*np.exp(x)+435/x*(np.exp(x)-1)-1564
pops='1000*exp(x)+435/x*(exp(x)-1)-1564'
a=10**-5; b=1
er=10**-3
r_aprox_coarda=coarda_er(pops, a, b, er)
r_exact=fsolve(pop, 10**-3)

