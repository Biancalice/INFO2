#ex6
import math
exp_exact=math.exp(1)
def exp_er(x, er):
    suma=0
    termen=1
    suma+=termen
    er_abs=abs(exp_exact-suma)
    er_rel=er_abs/abs(exp_exact)
    i=0
    while(er_rel>er):
        i+=1
        termen=termen*(x/i)
        suma+=termen
        er_abs=abs(exp_exact-suma)
        er_rel=er_abs/abs(exp_exact)
    return suma, i
x=1
exp_aprox_er,n=exp_er(x,10**(-14))
print(n)            
        