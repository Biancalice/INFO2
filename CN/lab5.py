#exercitiul 1
x=5/7
y=1/3
fl_x = 0.71428 * 10**0
fl_y = 0.33333 * 10**0
s=x+y
fl_s=fl_x+fl_y
print("Suma valorilor reale este:",s)
print("Suma aproximarilor este: ", fl_s)
d=x-y
fl_d=fl_x-fl_y
print("Diferenta valorilor reale este:",d)
print("Diferenta aroximarilor este:",fl_d)
p=x*y
fl_p=fl_x*fl_y
print("Produsul valorilor reale este:",p)
print("Produsul aproximarilor este:", fl_p)
c=x/y
fl_c=fl_x/fl_y
print("Catul valorilor reale este:",c)
print("Catul aproximarilor este:",fl_c)
ea1 = abs(s - fl_s)
print("Eroarea absoluta pentru suma:", ea1 )
ea2 = abs(d - fl_d)
print("Eroarea absoluta pentru diferenta:", fl_d)
ea3= abs(p - fl_p)
print("Eroarea absoluta pentru produs:", ea3 )
ea4= abs(c - fl_c)
print("Eroarea absoluta pentru cat:", ea4 )
er1 = ea1 / abs(s)
print("Eroarea relativa pentru suma:", er1 )
er2= ea2 / abs(d)
print("Eroarea relativa pentru diferenta:", er2 )
er3= ea3 / abs(p)
print("Eroarea relativa pentru produs:", er3 )
er4 = ea4/ abs(c)
print("Eroarea relativa pentru cat:", er4 )



#exercitiul2
u=0.714251
v=98765.9
w=0.1111111*10**-4
fl_u=0.71425*10**0
fl_v=0.98765*10**5
fl_w=0.111111*10**-4
d2=x-u
print("Diferenta este:",d2)
fl_d2=fl_x-fl_u
print("Diferenta aproximarilor:", fl_d2)
ea1_2=abs(d2-fl_d2)
print("Eroarea absoluta:", ea1_2)
er1_2=ea1_2/abs(d2)
print("Eroarea relativa:",er1_2)
c2=d2/w
print("Catul este:",c2)
fl_c2=fl_d2/fl_w
print("Catul aproximarilor:", fl_c2)
ea2_2=abs(c2-fl_c2)
print("Eroarea absoluta:", ea2_2)
er2_2=ea2_2/abs(c2)
print("Eroarea relativa:",er2_2)
p2=d2*v
print("Produsul este:",p2)
fl_p2=fl_d2*fl_v
print("Produsul aproximarilor:", fl_p2)
ea3_2=abs(p2-fl_p2)
print("Eroarea absoluta:", ea3_2)
er3_2=ea3_2/abs(p2)
print("Eroarea relativa:",er3_2)
s2=u+v
print("Suma este:",s2)
fl_s2=fl_u+fl_v
print("Suma aproximarilor:", fl_s2)
ea4_2=abs(s2-fl_s2)
print("Eroarea absoluta:", ea4_2)
er4_2=ea4_2/abs(s2)
print("Eroarea relativa:",er4_2)