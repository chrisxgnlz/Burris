import tkinter as tk
from tkinter import font as tkfont

class CalculatorApp:
    def __init__(self, root_window):
        self.root = root_window
        self.root.title("Calculadora Gráfica Burris")
        # self.root.geometry("300x400") # Optional: Set initial size like in Java
        self.root.resizable(False, False) # As in Java code

        # State Variables
        self.current_input_str = ""
        self.first_number_val = 0.0
        self.operator_str = ""

        # Display
        self.display_var = tk.StringVar()
        # Font similar to Segoe UI Bold 24 in Java
        display_font = tkfont.Font(family="Segoe UI", size=24, weight="bold")
        # Entry widget for display
        self.display_entry = tk.Entry(
            self.root,
            textvariable=self.display_var,
            font=display_font,
            justify='right',
            state='readonly', # Or 'disabled', 'readonly' is often preferred for copy-paste
            bd=5, # Border width
            relief=tk.SUNKEN, # Sunken border like many displays
            # background="white" # Tkinter Entry doesn't always show bg well with some themes
        )
        self.display_entry.pack(side=tk.TOP, fill=tk.X, padx=5, pady=5)

        # Button Panel Frame
        button_frame = tk.Frame(self.root)
        button_frame.pack(side=tk.TOP, fill=tk.BOTH, expand=True, padx=5, pady=5)
        
        # Configure rows and columns of the button_frame to expand proportionally (optional but good)
        for i in range(4): # 4 rows
            button_frame.grid_rowconfigure(i, weight=1)
        for i in range(4): # 4 columns
            button_frame.grid_columnconfigure(i, weight=1)

        buttons_layout = [
            "7", "8", "9", "+",
            "4", "5", "6", "C",
            "1", "2", "3", "=",
            "0", ".", "", ""
        ]

        button_font = tkfont.Font(family="Segoe UI", size=18, weight="bold")
        button_bg_color = "#E6E6FA"  # Lavender - approx (230, 230, 250)

        row, col = 0, 0
        for button_text in buttons_layout:
            if not button_text: # Empty string for placeholder
                # You could add an empty Label or just let grid handle it if preferred
                # For now, just advance grid position
                col += 1
                if col > 3:
                    col = 0
                    row += 1
                continue

            # Create button
            # No command assigned yet
            button = tk.Button(
                button_frame,
                text=button_text,
                font=button_font,
                bg=button_bg_color,
                relief=tk.RAISED,
                bd=3, # Border width for buttons
                command=lambda t=button_text: self.on_button_click(t) # Assign command
            )
            # Place button in grid, make it fill the cell
            button.grid(row=row, column=col, padx=5, pady=5, sticky="nsew")
            
            col += 1
            if col > 3:
                col = 0
                row += 1

    # Method to handle button clicks
    def on_button_click(self, button_text):
        if button_text.isdigit() or button_text == ".":
            # Prevent multiple leading zeros if current_input_str is "0"
            if button_text == "0" and self.current_input_str == "0":
                return
            # Prevent multiple dots
            if button_text == "." and "." in self.current_input_str:
                return
            # If current input is "0" and another digit is pressed, replace "0"
            if self.current_input_str == "0" and button_text != ".":
                self.current_input_str = button_text
            else:
                self.current_input_str += button_text
            self.display_var.set(self.current_input_str)

        elif button_text == "C":
            self.current_input_str = ""
            self.first_number_val = 0.0
            self.operator_str = ""
            self.display_var.set("")

        elif button_text == "+": # Only implementing '+' as per Java example
            if self.current_input_str: # Ensure there's something to parse
                try:
                    self.first_number_val = float(self.current_input_str)
                    self.operator_str = "+"
                    self.current_input_str = ""
                    # Display can be cleared or show first number. Java example clears.
                    self.display_var.set("") 
                except ValueError:
                    self.display_var.set("Error")
                    self.current_input_str = "" # Reset to avoid parsing error again
            # else: if current_input is empty, maybe allow changing operator if one was set?
            # For now, requires a number first.

        elif button_text == "=":
            if self.current_input_str and self.operator_str == "+": # Check for operator
                try:
                    second_number_val = float(self.current_input_str)
                    result = self.first_number_val + second_number_val
                    
                    # Display integer if result is whole number
                    if result.is_integer():
                        self.display_var.set(str(int(result)))
                        self.current_input_str = str(int(result))
                    else:
                        self.display_var.set(str(result))
                        self.current_input_str = str(result)
                    
                    self.operator_str = "" # Reset operator after calculation
                    # self.first_number_val = result # Optional: for continuous calculation like Windows calc
                except ValueError:
                    self.display_var.set("Error")
                    self.current_input_str = ""
            # else: do nothing if no operator or no second number

        # Other operators like -, *, / are not implemented as per Java code.
        
if __name__ == "__main__":
    app_root = tk.Tk()
    calculator_gui = CalculatorApp(app_root)
    app_root.mainloop()
```
