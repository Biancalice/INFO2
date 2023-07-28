import math
import numpy as np
import matplotlib
from matplotlib import pyplot as plt
#lab 3 08.03.2023
#exercitiul 1 
a=math.ceil(3.25)
print(a)
a1=math.floor(3.25)
print(a1)
a2=math.trunc(3.25)
print(a2)

b=math.ceil(3.75)
print(b)
b1=math.floor(3.75)
print(b1)
b2=math.trunc(3.75)
print(b2)

c=math.ceil(-3.25)
print(c)
c1=math.floor(-3.25)
print(c1)
c2=math.trunc(-3.25)
print(c2)

d=math.ceil(-3.75)
print(d)
d1=math.floor(-3.75)
print(d1)
d2=math.trunc(-3.75)
print(d2)

#exercitiul 2
x=math.pow(10,5)
print(x)
x1=math.pow(5,10)
print(x1)
x2=math.pow(x, 10.01) #x**10.01
print(x2)
x3=math.sqrt(math.pow(10,5))
print(x3)
x4=math.pow(10, 3/5)
print(x4)
x5=math.exp(math.sin(5))
print(x5)
x6=math.exp(1-math.log(math.fabs(1+x**3),5))
print(x5)
x6=math.pow(2,math.log(1+x**3,5))
print(x6)

#exercitiul 3
z=np.arange(5, 20.5, .5)
y1=10**z
y2=z**10
y3=z**10.01
y5=10**z/5
y6=np.exp(np.sin(z))
y7=np.exp(1-np.log(np.fabs(1+z**3),z))
y8=2**(1-np.log((1+z**3),z))
plt.plot(z,y1)
plt.plot(z,y2)
plt.plot(z,y3)
plt.plot(z,y5)
plt.plot(z,y6)
plt.plot(z,y7)
plt.plot(z,y8)
plt.legend(['functia a' , 'functia b'])
plt.subplot(2,4,1)
plt.show()

#exercitiul4
fig=plt.figure()
plt.plot(z,y7)
plt.scatter(z,y8)
plt.title(r'$\phi_7\ and\ \phi_8$')
plt.xlabel('Var. independenta')
plt.ylabel('Var. dependenta')
plt.legend(['funcatia g ' , 'functia h'])
plt.xlim([0,30])
plt.ylim([0,.5])
plt.xticks(np.arange(0,30,10),['zero', '10', 'douazeci'])
















