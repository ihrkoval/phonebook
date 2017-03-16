Таблицы создаются с помощью hibernate

VMoptions like:
-Dlardi.conf=C:/TEMP/config.properties

---------------*.properties example for MySQL

spring.datasource.url=jdbc:mysql://localhost:3306/test

spring.datasource.username=root

spring.datasource.password=toor

spring.datasource.driver-class-name=com.mysql.jdbc.Driver

spring.jpa.database=MYSQL

spring.jpa.show-sql=true

spring.jpa.hibernate.ddl-auto=update

spring.mvc.view.prefix=/WEB-INF/jsp/

spring.mvc.view.suffix=.jsp


-----------------------------------------------------------------


---------------*.properties example for CSV

spring.mvc.view.prefix=/WEB-INF/jsp/

spring.mvc.view.suffix=.jsp

spring.datasource.url=jdbc:relique:csv:C:\\TEMP\\1\\

spring.datasource.driver-class-name=org.relique.jdbc.csv.CsvDriver

spring.jpa.database=H2

spring.jpa.show-sql=true


---------------------------------------------------------------
