import math
from math import modf

#Ex7
def exp_aprox(x,n):
    suma = 0
    for i in range(n):
        termen = (x**i)/math.factorial(i)
        suma+=termen
    return suma    

x = math.sqrt(5)
pf,pi = modf(x)
p = 1
pi = int(pi)
for i in range(pi):
  p*=exp_aprox(1,10)  
   
p*=exp_aprox(pf,10)


esqrt5_exact =math.exp(x)
er_abs = abs(esqrt5_exact-p)
er_rel = er_abs/abs(esqrt5_exact)

#Ex8
def ln_aprox(z,n):
    x=(1-z)/(1+z)
    ti=x
    s=ti
    for i in range(1,n):
        ti*=(x**2)
        s+=1/(2*i+1)*ti
    return -2*s

ln7_aprox=ln_aprox(7,10)
ln7_exact=math.log(7)

er_abs = abs(ln7_exact-ln7_aprox)
er_rel = er_abs/abs(ln7_exact) 


def ln_aprox_er(z,er):
    i=1
    x=(1-z)/(1+z)
    ti=x
    ln_exact=math.log(z)
    s=ti*(-2)
    er_rel=abs(ln_exact-s)/abs(ln_exact)
    while er_rel>er:
        ti*=(x**2)
        s+=1/(2*i+1)*ti*(-2)
        i+=1
        er_rel=abs(ln_exact-s)/abs(ln_exact)
    return s,i

ln7_aprox_er,n=ln_aprox_er(7,10**-10)
ln7_exact_er=math.log(7)

er_abs_er = abs(ln7_exact_er-ln7_aprox_er)
er_rel_er = er_abs_er/abs(ln7_exact_er) 

#log2((x))=lnx/ln2

def sin23_aprox(x,n):
    ti=x
    s=ti
    for i in range(1,n):
        ti*=((-1)*(x**2))/(2*i*(2*i+1))
        s+=ti
    return s

sin23_aprox=sin23_aprox(math.radians(23),10)
sin23_exact=math.sin(math.radians(23))
    
er_abs_sin = abs(sin23_exact-sin23_aprox)
er_rel_sin= er_abs_sin/abs(sin23_exact)

#Ex9
def sqrt89_aprox(alfa,x):
    x=alfa
    for i in range(1,n):
        x=(1/2)*(x+(alfa/n))
    return x
        
sqrt89_aprox=sqrt89_aprox(89,10)
sqrt89_exact=math.sqrt(89)

er_abs_sqrt = abs(sqrt89_exact-sqrt89_aprox)
er_rel_sqrt= er_abs_sin/abs(sqrt89_exact)

    




