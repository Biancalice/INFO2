import matplotlib.pyplot as plt
import numpy as np
import math
def euler(f, x0, y0, h, x_max):
    x = [x0]
    y = [y0]
    while x[-1] < x_max:
        x.append(x[-1] + h)
        y.append(y[-1] + h*f(x[-2], y[-1]))
    return x, y
def rk4(f, x0, y0, h, x_max):
    x = [x0]
    y = [y0]
    while x[-1] < x_max:
        k1 = h * f(x[-1], y[-1])
        k2 = h * f(x[-1] + h/2, y[-1] + k1/2)
        k3 = h * f(x[-1] + h/2, y[-1] + k2/2)
        k4 = h * f(x[-1] + h, y[-1] + k3)
        x.append(x[-1] + h)
        y.append(y[-1] + 1/6 * (k1 + 2*k2 + 2*k3 + k4))
    return x, y

def f(x, y):
    return 2*x-2*y

def exact(x):
    return (x + 0.5 * np.exp(-2*x))


x0, y0, x_max = 0, 1, 2
h = 0.1

# calculam solutia exacta
x_exact = np.linspace(x0, x_max, 100)
y_exact = exact(x_exact)

# calculam solutia aproximata cu metoda lui Euler
x_euler, y_euler = euler(f, x0, y0, h, x_max)

# calculam solutia aproximata cu metoda Runge-Kutta de ordin patru
x_rk4, y_rk4 = rk4(f, x0, y0, h, x_max)

# desenam graficele
plt.plot(x_exact, y_exact, label='Solutia exacta', color='black')
plt.plot(x_euler,y_euler,'o-',label='Metoda Euler')
plt.plot(x_rk4, y_rk4,'s-', label='Metoda Runge-Kutta de ordin patru',color='yellow')
plt.legend()
plt.xlabel('x')
plt.ylabel('y')
plt.show()

err_euler = [abs(y_exact[i] - y_euler[i]) for i in range(len(x_euler))]
err_rk4 = [abs(y_exact[i] - y_rk4[i]) for i in range(len(x_rk4))]

plt.plot(x_euler, err_euler, label='Eroarea metodei lui Euler')
plt.plot(x_rk4, err_rk4, label='Eroarea metodei Runge-Kutta de ordin patru')
plt.legend()
plt.xlabel('x')
plt.ylabel('Eroare')
plt.show()
