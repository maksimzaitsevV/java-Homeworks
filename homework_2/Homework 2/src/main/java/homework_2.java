import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URL;
import java.util.Scanner;

public class homework_2 {
    public static class Task1 {
        public static void main(String[] args) {
            var in = new Scanner(System.in);
            var n = in.nextInt();

            if (n == 0) {
                System.out.println(0);
                return;
            }
            var rnd = new Random();
            var rndArray = new int[n];

            for (var i = 0; i < n; i++) {
                rndArray[i] = rnd.nextInt();
            }

            for (var i = 0; i < n; i++) {
                System.out.print(rndArray[i]);
                if (i < rndArray.length - 1) System.out.print(" ");
            }

            System.out.println("\n" + getMinAbsValue(rndArray));

            in.close();
        }

        public static int getMinAbsValue(int[] array) {
            var min = Integer.MAX_VALUE;
            for (int j : array) {
                if (Math.abs(j) < min) min = Math.abs(j);
            }
            return min;
        }
    }

    public static class Task2 {
        public static void main(String[] args) {
            var in = new Scanner(System.in);
            var n = in.nextInt();

            if (n == 0) {
                System.out.println(0);
                return;
            }

            var rnd = new Random();
            var rndList = new ArrayList<Double>();

            for (var i = 0; i < n; i++) {
                rndList.add(rnd.nextDouble());
            }

            for (var i = 0; i < n; i++) {
                System.out.print(rndList.get(i));
                if (i < n - 1) System.out.print(" ");
            }

            var sorted = QuickSort(rndList, 0, n - 1);

            System.out.println("\n" + QuickSort(rndList, 0, n - 1));

            in.close();
        }

        public static ArrayList<Double> QuickSort(ArrayList<Double> list, int start, int end) {
            var sorted = new ArrayList<>(list);
            if (start < end) {
                var pivotIndex = start + new Random().nextInt(end - start + 1);
                var pivot = sorted.get(pivotIndex);
                var l = start;
                var r = end;
                while (l <= r) {
                    while (sorted.get(l) < pivot) l++;
                    while (sorted.get(r) > pivot) r--;
                    if (l <= r) {
                        Collections.swap(sorted, l, r);
                        l++;
                        r--;
                    }
                }
                sorted = QuickSort(sorted, start, r);
                sorted = QuickSort(sorted, l, end);
            }
            return sorted;
        }
    }

    public static class Task3 {
        public static class Employee {
            private String fullName;
            private Integer age;
            private String department;
            private Double salary;

            public Employee(String fullName, Integer age, String department, Double salary) {
                this.fullName = fullName;
                this.age = age;
                this.department = department;
                this.salary = salary;
            }

            public String getFullName() {
                return fullName;
            }

            public Integer getAge() {
                return age;
            }

            public String getDepartment() {
                return department;
            }

            public Double getSalary() {
                return salary;
            }

            public void setFullName(String fullName) {
                this.fullName = fullName;
            }

            public void setAge(Integer age) {
                this.age = age;
            }

            public void setDepartment(String department) {
                this.department = department;
            }

            public void setSalary(Double salary) {
                this.salary = salary;
            }

            public static ArrayList<Employee> createEmployee() {
                var EmployeeList = new ArrayList<Employee>();
                EmployeeList.add(new Employee("Ярмошенко Александр Ильич", 20, "IT", 100000.1));
                EmployeeList.add(new Employee("Фурман Максим Валерьевич", 20, "IT", 99999.9));
                EmployeeList.add(new Employee("Горин Генадий Александрович", 52, "YT", 200000.2));
                EmployeeList.add(new Employee("Пушкин Александр Сергеевич", 37, "Finance", 20000.5));
                EmployeeList.add(new Employee("Маск Илон Рив", 54, "HR", 50000.9));

                return EmployeeList;
            }
        }

        public static class Solution {
            public static void main(String[] args) {
                var in = new Scanner(System.in);
                var requiredDepartment = in.nextLine();

                if (requiredDepartment == null) {
                    System.out.println(0);
                    return;
                }

                var employeeList = Employee.createEmployee();

                var departmentEmployees = employeeList.stream().filter(employee -> Objects.equals(employee.getDepartment(), requiredDepartment)).toList().stream().mapToDouble(Employee::getSalary).average().orElse(0.0);

                System.out.println(departmentEmployees);

                in.close();
            }
        }
    }

    public static class Task4 {
        public static void main(String[] args) throws IOException, InterruptedException {
            try (var client = HttpClient.newHttpClient()) {
                var request = HttpRequest.newBuilder().uri(URI.create("https://httpbin.org/user-agent")).build();
                var response = client.send(request, HttpResponse.BodyHandlers.ofString());
                var body = response.body();
                var objectMapper = new ObjectMapper();
                var deserializedJson = objectMapper.readTree(body);
                System.out.println(deserializedJson.get("user-agent").asText());
            }
        }
    }

    public static class Task5 {
        interface Task {
            void start();

            void stop();
        }

        public static class DownloadFile implements Task {
            private boolean isStopped = false;
            private String url;
            private String downloadPath;

            public DownloadFile(String url, String path) {
                this.url = url;
                this.downloadPath = path;
            }

            public static void main(String[] args) {
                var path = System.getProperty("user.home") + "/Desktop/downloaded_image.jpg";
                var downloadFile = new DownloadFile("https://www.powercoin.it/img/cms/New%20Folder/Untitled-6.png", path);
                downloadFile.start();
            }

            private void downloadFile() {
                try (var inputStream = new URL(url).openStream(); var bufferedInputStream = new BufferedInputStream(inputStream); var fileOutputStream = new FileOutputStream(downloadPath);) {
                    var byteBlock = new byte[1024];
                    var bytesRead = 0;
                    while ((bytesRead = bufferedInputStream.read(byteBlock)) != -1) {
                        if (isStopped) {
                            System.out.println("stop loading");
                            new File(downloadPath).delete();
                            return;
                        }
                        fileOutputStream.write(byteBlock, 0, bytesRead);
                    }
                    System.out.println("file uploaded");
                } catch (IOException error) {
                    new File(downloadPath).delete();
                }
            }

            @Override
            public void start() {
                isStopped = false;
                System.out.println("loading has started");
                downloadFile();
            }

            @Override
            public void stop() {
                isStopped = true;
                System.out.println("download stopped");
            }
        }
    }
}

