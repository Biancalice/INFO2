#ex5
import math
import numpy as np
exp_exact=math.exp(1)
def exp_iter(x,n):
    suma = 0
    for i in range(n):
        termen = (x**i)/math.factorial(i)
        suma+=termen
    return suma
          
#a).
x=1
n=5
exp_aprox_5=exp_iter(x,n)
er_abs_5=abs(exp_exact-exp_aprox_5)
er_rel_5=er_abs_5/abs(exp_exact)

#b).
x=1
n=10
exp_aprox_10=exp_iter(x,n)
er_abs_10=abs(exp_exact-exp_aprox_10)
er_rel_10=er_abs_10/abs(exp_exact)