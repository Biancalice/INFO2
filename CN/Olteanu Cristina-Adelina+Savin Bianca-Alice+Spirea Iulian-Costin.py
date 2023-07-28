import sys
import numpy as np
from sympy import Symbol, diff, lambdify
from scipy.optimize import fsolve
from matplotlib.backends.backend_qt5agg import FigureCanvasQTAgg as FigureCanvas
from matplotlib.figure import Figure
from PyQt5.QtWidgets import (
    QApplication,
    QMainWindow,
    QLabel,
    QLineEdit,
    QPushButton,
    QMessageBox,
    QRadioButton,
    QVBoxLayout,
    QWidget,
    QSizePolicy,
)
import time


class MatplotlibCanvas(FigureCanvas):
    def __init__(self, figure):
        super().__init__(figure)
        self.setParent(None)
        self.setSizePolicy(QSizePolicy.Expanding, QSizePolicy.Expanding)
        self.updateGeometry()


class MainWindow(QMainWindow):
    def __init__(self):
        super().__init__()
        self.setWindowTitle("Interfață")
        self.setup_ui()

    def setup_ui(self):
        self.resize(1200, 700)

        label_function = QLabel("Funcție:", self)
        label_function.move(10, 10)
        self.entry_function = QLineEdit(self)
        self.entry_function.move(10, 30)

        label_a = QLabel("a:", self)
        label_a.move(10, 60)
        self.entry_a = QLineEdit(self)
        self.entry_a.move(10, 80)

        label_b = QLabel("b:", self)
        label_b.move(10, 110)
        self.entry_b = QLineEdit(self)
        self.entry_b.move(10, 130)

        label_method = QLabel("Metodă de calcul:", self)
        label_method.move(10, 160)
        self.selected_method = "bisectie"
        self.radio_button_bisectie = QRadioButton("Metoda bisectiei", self)
        self.radio_button_bisectie.setChecked(True)
        self.radio_button_bisectie.move(10, 180)
        self.radio_button_bisectie.resize(200, 30)
        self.radio_button_coarda = QRadioButton("Metoda coardei", self)
        self.radio_button_coarda.move(10, 200)
        self.radio_button_coarda.resize(200, 30)

        label_er = QLabel("Eroare:", self)
        label_er.move(10, 230)
        self.entry_er = QLineEdit(self)
        self.entry_er.move(10, 250)

        label_nr = QLabel("Numar de iteratii:", self)
        label_nr.move(10, 260)
        self.entry_nr = QLineEdit(self)
        self.entry_nr.move(10, 280)

        button = QPushButton("Calculează", self)
        button.move(10, 300)
        button.clicked.connect(self.on_button_click)

        self.result_label = QLabel(self)
        self.result_label.move(10, 310)
        self.result_label.setText("Rezultatul aproximării:")
        self.result_label.resize(300, 30)

        self.error_label = QLabel(self)
        self.error_label.move(10, 340)
        self.error_label.setText("Eroarea absolută:")
        self.error_label.resize(300, 30)

        self.time_label = QLabel(self)
        self.time_label.move(10, 370)
        self.time_label.setText("Elapsed Time Method:")
        self.time_label.resize(300, 30)

        layout = QVBoxLayout()
        layout.addWidget(label_function)
        layout.addWidget(self.entry_function)
        layout.addWidget(label_a)
        layout.addWidget(self.entry_a)
        layout.addWidget(label_b)
        layout.addWidget(self.entry_b)
        layout.addWidget(label_method)
        layout.addWidget(self.radio_button_bisectie)
        layout.addWidget(self.radio_button_coarda)
        layout.addWidget(label_er)
        layout.addWidget(self.entry_er)
        layout.addWidget(label_nr)
        layout.addWidget(self.entry_nr)
        layout.addWidget(button)
        layout.addWidget(self.result_label)
        layout.addWidget(self.error_label)
        layout.addWidget(self.time_label)

        self.canvas = MatplotlibCanvas(Figure(figsize=(6, 4), dpi=100))
        layout.addWidget(self.canvas)

        central_widget = QWidget()
        central_widget.setLayout(layout)
        self.setCentralWidget(central_widget)

    def on_button_click(self):
        fs = self.entry_function.text()
        a = self.validate_input(self.entry_a.text(), "a")
        b = self.validate_input(self.entry_b.text(), "b")
        er = self.validate_input(self.entry_er.text(), "Eroare")
        nr = self.validate_input(self.entry_nr.text(), "Numar de iteratii")

        if None in [a, b, er, nr]:
            return

        try:
            x = np.linspace(a, b, 100)
            y = eval(fs)

            ax = self.canvas.figure.add_subplot(111)
            ax.plot(x, y, "b-", label="Funcție")
            ax.set_xlabel("x")
            ax.set_ylabel("f(x)")
            ax.set_title("Graficul funcției")
            ax.grid(True)
            ax.legend()

            self.canvas.draw()

            start_time = time.time()
            result = self.coarda_er(fs, a, b, er, nr)
            result = self.bis_tol(fs, a, b, er, nr)
            end_time = time.time()
            elapsed_time = end_time - start_time

            self.result_label.setText(f"Rezultatul aproximării: {result:.6f}")

            exact_result = self.coarda_er(fs, a, b, 0, nr)
            exact_result = self.bis_tol(fs, a, b, 0, nr)
            absolute_error = abs(exact_result - result)
            self.error_label.setText(f"Eroarea absolută: {absolute_error:.6f}")

            self.time_label.setText(f"Elapsed Time: {elapsed_time:.6f} sec")

        except Exception as e:
            QMessageBox.critical(self, "Eroare", f"A apărut o eroare: {str(e)}")

    def validate_input(self, text, field_name):
        try:
            value = float(text)
            return value
        except ValueError:
            QMessageBox.warning(
                self,
                "Eroare",
                f"Campul '{field_name}' poate fi completat doar cu numere.",
            )
            return None

    def coarda_er(self, f, a, b, er, nr):
        x = Symbol("x")
        f2 = diff(f, x, 2)
        f = lambdify(x, f)
        f2 = lambdify(x, f2)
        r_ex = fsolve(f, (a + b) / 2)

        if f(a) * f2(a) < 0:
            x = a
            iteration = 0
            while abs(r_ex - x) > er and iteration < nr:
                x = x - f(x) / (f(x) - f(b)) * (x - b)
                iteration += 1
        else:
            if f(b) * f2(b) < 0:
                x = b
                iteration = 0
                while abs(r_ex - x) > er and iteration < nr:
                    x = x - f(x) / (f(x) - f(a)) * (x - a)
                    iteration += 1

        return x

    def bis_tol(self, f, a, b, er, nr):
        x = Symbol("x")
        f = lambdify(x, f)
        iteration = 0
        while (b - a) / 2 > er and iteration < nr:
            c = (a + b) / 2
            iteration += 1
            if f(c) == 0:
                return c
            elif f(a) * f(c) < 0:
                b = c
            else:
                a = c

        return (a + b) / 2


app = QApplication(sys.argv)
window = MainWindow()
window.show()
sys.exit(app.exec())