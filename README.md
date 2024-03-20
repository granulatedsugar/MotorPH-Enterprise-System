![banner-mph](https://github.com/granulatedsugar/MotorPH-Enterprise-System/assets/48410720/d354cf27-3b05-48ae-afaa-8b2fcd575c82)

Welcome to the MotorPH Payroll System, a comprehensive and user-friendly solution developed to manage the payroll of MotorPH. This system is designed to streamline the payroll process and make it more efficient, accurate and secure. It enables the company to easily track employee salaries, work hours, deductions, and other compensation related information. The system is fully automated, eliminating the need for manual data entry and reducing the chances of errors. The user-friendly interface of the system makes it easy for the HR and finance departments to manage payroll-related tasks with ease. The MotorPH Console Payroll System is a reliable, secure and scalable solution that will help MotorPH to stay ahead in the competitive business world.

## Getting Started
These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites
1. Java SDK
2. IDE IntelliJ, Eclipse, or Net Beans
3. MySQL Community or Laragon

### Installation
1. Installing Java:
```
-First, visit the official website of Java and download the latest version of Java SE Development Kit (JDK).
-Once the download is complete, double click on the executable file and follow the on-screen instructions to install Java on your computer.
-After the installation is complete, you need to set the environment variables for Java. To do this, go to the Control Panel and select System and Security. Then select -System and click on Advanced system settings.
-In the Advanced tab, click on the Environment Variables button. Then, under System Variables, find the Path variable and click on Edit.
-Add the location of the bin folder of your Java installation to the Path variable. (e.g. C:\Program Files\Java\jdk1.8.0_241\bin)
-Restart your computer and Java will be installed and ready to use.
```
2. Installing an IDE:
```
-Choose one of the following IDEs - IntelliJ, Eclipse, or Net Beans.
-Visit the official website of the selected IDE and download the latest version of the software.
-Once the download is complete, double click on the executable file and follow the on-screen instructions to install the IDE.
-After the installation is complete, launch the IDE and start using it for your Java development.
```
3. Installing Mysql or Laragon Full:
```
-Choose one of the following databases - Mysql or Laragon Full.
-If you have chosen Mysql, visit the official website of Mysql and download the latest version of Mysql Community Server.
-Once the download is complete, double click on the executable file and follow the on-screen instructions to install Mysql on your computer.
-If you have chosen Laragon Full, visit the official website of Laragon and download the latest version of Laragon Full.
-Once the download is complete, double click on the executable file and follow the on-screen instructions to install Laragon Full on your computer.
-After the installation is complete, launch the database and start using it for your Java development.
```
### Database Setup
```
-Run motorph.sql found in the sql directory in MySQL or Laragon.
-Please refer to the DBUtil.java class when setting up the connection.
```
### Roles
```
-Roles are implemented.
-Each role has specific functions assigned.
```

## Deployment
Welcome to the MotorPH Payroll System! In this tutorial, we will guide you on how to run this application by following the screenshots. The application has been developed to manage the payroll of a company and it is user-friendly and efficient. By following the steps in this tutorial, you will be able to run the application and manage the payroll process with ease. We will provide you with clear and concise instructions along with screenshots to help you run the application smoothly. So, let's get started and run the MotorPH Payroll System!

#### Login Screen
* **To login, you need username and a password.**
* **Username: Use the email address associated with the employee account.**
* **List of username: mgarcia@motorph.com, alim@motorph.com, baquino@motorph.com, ireyes@motorph.com, ehernandez@motorph.com, avillanueva@motorph.com, bsanjose@motorph.com, aromualdez@motorph.com, ratienza@motorph.com,...,ralvaro@motorph.com**
* **Password: Use the default password "password".**

<img src="https://github.com/granulatedsugar/MotorPH-Enterprise-System/assets/48410720/a89757b2-1326-406d-9ccf-2547cdca1e42" alt="login">
<img src="https://github.com/granulatedsugar/MotorPH-Enterprise-System/assets/48410720/6e97f50e-6463-4736-a6ba-f118342484be" alt="create-pass">

#### Welcome Screen
* **Upon successful login, you will be automatically redirected to the welcome screen.**
* **The welcome screen will display the name of the logged-in user.**
<img src="https://github.com/granulatedsugar/MotorPH-Enterprise-System/assets/48410720/b6ed58af-337f-4e04-a88e-b236cb740ca1" alt="create-pass">

#### Dashboard
* **Dashboard page displays an overview of the company.**
* **Information includes: Number of employees, Total products, Payroll expenses, Employee status breakdown, Expense breakdown.**
![image](https://github.com/granulatedsugar/MotorPH-Enterprise-System/assets/48410720/9a0fd119-88d5-41c8-995e-8cdd42750e54)

#### Employee List
* **Employee module consists of two functions: Employee Details, Add/Update Employees**
* **Note: Access to these functionalities may be restricted for certain employees.**
![image](https://github.com/granulatedsugar/MotorPH-Enterprise-System/assets/48410720/88b46fd9-9063-4aa7-93d6-6a9c3d1c9492)

#### Add/Update Employee
* **To add a new employee click the "Add" button on the left side bar, Fill in all the required fields, then click the "Add" on the bottom right corner**
* **To update an existing employee, Click the "Details" button and select an employee row and press the pen icon on the right side, Update any of the fields as needed, press the update button on the bottom right corner**
![image](https://github.com/granulatedsugar/MotorPH-Enterprise-System/assets/48410720/976a43b4-28f9-42ec-8b0b-648e86335cdd)

#### Attendance
* **Attendance module consists of four functions: Time in/ Time out, Manage Attendance (Log), Manage Leave, Leave Request**
* **Note: Access to these functionalities may be restricted for certain employees.**

![image](https://github.com/granulatedsugar/MotorPH-Enterprise-System/assets/48410720/8ef59a42-f8d7-44ce-98df-6d8f9367cd40)
* **Some employee can create a manual attendance**
* **Click the "Log", click "+ New Attendance", Complete all fields, and Click Submit.**

![image](https://github.com/granulatedsugar/MotorPH-Enterprise-System/assets/48410720/2f4a5fc7-2868-4eec-8b42-352452f35f45)
* **Click "Time In/Out" in the side menu, then punch in when logging in and punch out when logging out on the stage that pops out.**

##### File Leave Request
![image](https://github.com/granulatedsugar/MotorPH-Enterprise-System/assets/48410720/0d7d00a9-41b3-4b5a-8d01-4ad360c9d223)
* **To file a leave request, the user should click on "Leave Request", Select Start/End Dates and Leave Type, and Click the submit button.**

![image](https://github.com/granulatedsugar/MotorPH-Enterprise-System/assets/48410720/ccf9249d-40a5-4251-a72a-af2d5d7e338f)
* **Some employees can create a manual leave request**
* **Click the "Manage Leave", Click "File a Leave", Complete the required fields in the displayed stage, and click submit button.**
* **This is for certain employees that forgot to file a leave.**

#### Payroll
* **Payroll module consists of two functions: Payslips, New Payslips**
* **Note: Access to these functionalities may be restricted for certain employees.**
![image](https://github.com/granulatedsugar/MotorPH-Enterprise-System/assets/48410720/085122e5-e1a4-4760-a3a5-e46a8342d1ec)
* **User can create a new payslip by clicking the "New Payslip" in the sidebar or by clicking "Payslips", and " + New Payslip".**
* **In the creating a new payslip, the user should click the override checkbox, Type Employee Id, Select Start and End Date, click generate.**
* **The system automatically calculate everything and fill all the fields.**

![image](https://github.com/granulatedsugar/MotorPH-Enterprise-System/assets/48410720/14d94ce6-b396-428e-8f2f-51a9d6e76546)

#### Administration
* **Administration module consists of four functions: Users, User Roles, Position, Department**
* **Note: Access to these functionalities may be restricted for certain employees.**
![image](https://github.com/granulatedsugar/MotorPH-Enterprise-System/assets/48410720/dcdd44b8-92d3-4f33-99e6-31b40f0023e3)
![image](https://github.com/granulatedsugar/MotorPH-Enterprise-System/assets/48410720/21081f1f-a5d8-4d0c-bb2e-ea729d9fd135)



## Built With
* [Maven](https://maven.apache.org/) - Dependency Management
* Scene Builder
* MaterialFX

## Authors
* **Nicholas Roque**
* **Sid Tolentino**
* **Taylor Tayengco**

## Documents
* [Use Case Diagram](https://lucid.app/lucidchart/e3aa0238-decc-4b32-8b14-58f21b83ad63/edit?viewport_loc=-750%2C-150%2C4039%2C1896%2C.Q4MUjXso07N&invitationId=inv_081452c2-a4ca-4e7c-a076-6a5be3b217f8)
* [Test Cases](https://docs.google.com/spreadsheets/d/1fQqnoPAjFw55cTYgn-U8fvzfwBrAuhWm6lzDbZaJLEM/edit?usp=sharing)

## Acknowledgments
* Armando Sta. Cruz III
* Mapua Malayan Digital College

