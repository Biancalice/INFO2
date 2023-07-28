#exercitiul3
import math
#x**2+62.10*x+1=0
a = 1
b = 62.10
c = 1
delta = b ** 2 - 4 * a * c
print("Delta=",delta)
x1 = (-b + math.sqrt(delta)) / (2 * a)
x2 = (-b - math.sqrt(delta)) / (2 * a)
print("Rădăcinile ecuației sunt:")
print(f"x1 = {x1}")
print(f"x2 = {x2}")
fl_x1 = -0.01610723
fl_x2 = -62.08390
ea1=abs(x1-fl_x1)
print("Eroarea absoluta:",ea1)
er1=ea1/abs(x1)
print("Eroarea relativa:", er1)
ea2=abs(x2-fl_x2)
print("Eroarea absoluta:",ea2)
er2=ea2/abs(x2)
print("Eroarea relativa:", er2)
x1_r = (-2 * c) / (b +math.sqrt(delta))
x2_r = (-2 * c) / (b - math.sqrt(delta))
print("Rădăcinile obținute dupa raționalizare:")
print("x1 =", x1_r)
print("x2 =", x2_r)
ea3=abs(x1_r-fl_x1)
print("Eroarea absoluta:",ea3)
er3=ea3/abs(x1_r)
print("Eroarea relativa:", er3)
ea4=abs(x2_r-fl_x2)
print("Eroarea absoluta:",ea4)
er4=ea4/abs(x2_r)
print("Eroarea relativa:", er4)