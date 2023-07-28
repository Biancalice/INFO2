#laborator2 calcul numeric-01.03.2023
import numpy as np
#exercitiul 1
a=np.random.rand(4,3)*2+8
a=np.random.rand(4,3)*(-2)+10

#exercitiul 2
a1=np.random.randint(5,11,(3,10))

#exercitiul 3
a[: 2, -3 :]=0

#exercitiul 4
a2=np.full(30,2)

#exercitiul 5
a2=np.reshape(a2,(3,10))
print(a2)

#exercitiul 6
a3=a2**a1
print(a3)


#exercitiul 7

a4=a1**a2
print(a4)



#exercitiul 8 
a5=np.linalg.norm(a1[:,0])
print(a5)

