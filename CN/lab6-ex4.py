#ex4
import numpy as np
from numpy.polynomial import Polynomial as P
x=4.71
#(a)-metoda clasica
a=[1.5, 3.2, -6.1, 1]
puteri=[1, x, x*2, x*3]
f=np.dot(a, puteri)

#(b)-schema lui Horner
a_r=np.array(a)[::-1]
f_horner=a_r[0]
for i in a_r[1:]:
    f_horner=f_horner*x+i
    print(f_horner)
    
#(c)-valoarea 'exacta'
p=P(a)
f_exact=p(x)

#erori pt (a)
er_abs_a=abs(f_exact-f)
er_rel_a=er_abs_a/abs(f_exact)

#erori pt (b)
er_abs_b=abs(f_exact-f_horner)
er_rel_b=er_abs_b/abs(f_exact)


