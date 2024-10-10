
public class Employee  implements Comparable<Employee> {

	// The Employee's attributes
	private String employeeID;
	private String name;
	private int age;
	private String department;
	private String dateOfJoining;
	private char gender;

	// No-arg constructor
	public Employee() {
		this.employeeID = "E000";
		this.name = "Hanadi";
		this.age = 20;
		this.department = "Computer Science";
		this.dateOfJoining = "2024-8-18";
		this.gender = 'F';
	}

	// Constructor with fields
	public Employee(String employeeId, String name, int age, String department, String dateOfJoining, char gender) {
		super();
		setEmployeeId(employeeId);
		setName(name);
		setAge(age);
		setDepartment(department);
		setDateOfJoining(dateOfJoining);
		setGender(gender);
	}

	// Constructor with string
	public Employee(String line) {

		if(line != null) {
			String[] parts = line.split(",");
			if (parts.length == 6) {
				setEmployeeId(parts[0].trim());
				setName(parts[1].trim());
				setAge(Integer.parseInt(parts[2].trim()));
				setDepartment(parts[3]);
				setDateOfJoining(parts[4].trim()); // DateOfJoining format yyyy-MM-dd
				setGender((parts[5].trim()).charAt(0));
			}
		}
	}

	// The getter and setters of the attributes
	public String getEmployeeID() {
		return employeeID;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeID = employeeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		if(name.matches("[A-Za-z ]+"))
			this.name = name;
		else
			System.out.println("Error mismatch !!\n The name must be alphabetical");
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {

		if (age > 0 && age <= 120)
			this.age = age;
		else
			System.out.println("age must be [1-120] ");
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getDateOfJoining() {
		return dateOfJoining;
	}

	public void setDateOfJoining(String dateOfJoining) {
		String[] date = dateOfJoining.split("-");

		if (date.length == 3) {
			try {
				int year = Integer.parseInt(date[0]);
				int month = Integer.parseInt(date[1]);
				int day = Integer.parseInt(date[2]);
				
				if (year >= 1990 && month > 0 && month <= 12 && day > 0 && day <= 30)
					this.dateOfJoining = dateOfJoining;
				else
					System.out.println("Date Out of Range!");

			} catch (NumberFormatException ex) {
				System.out.println(ex.getMessage());
			}
		} else
			System.out.println("Date missmatch format -> yyyy-mm-dd ");
	}

	public char getGender() {
		return gender;
	}

	public void setGender(char gender) {
		if (gender == 'F' || gender == 'M')
			this.gender = gender;
		else
			System.out.println("Gender must be (F/M)");
	}

	@Override
	public String toString() {
		return "Employee [employeeId: " + employeeID + ", name: " + name + ", age: " + age + ", department: "
				+ department + ", dateOfJoining: " + dateOfJoining + ", gender: " + gender + "]";
	}

	// Comparing depend on EmployeeId
	@Override
	public int compareTo(Employee o) {
		return this.employeeID.compareTo(o.employeeID);
	}

}
