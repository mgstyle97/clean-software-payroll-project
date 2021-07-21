# 클린 소프트웨어 디자인 패턴 적용(급여 관리 프로젝트)



### *Description*

클린 소프트웨어 Chapter 13, 14, 15, 16, 17 장에서 학습한 디자인 패턴을 직접 적용 시킨 급여 관리 프로젝트입니다.

##### 사용한 디자인 패턴

- 커맨드 패턴
- 퍼사드 패턴
- 템플릿 메서드 패턴
- 널 오브젝트 패턴
- 스트래터지 패턴



#### 커맨드 패턴

###### *Scenario*

급여 관리 프로젝트는 사용자로부터 트랜잭션을 입력받아 입력받은 명령을 기반으로 트랜잭션을 수행한다.

여기서 Transaction이라는 추상 기반 클래스를 도출해낼 수 있고, 이 클래스는 execute()라는 이름의 인스턴스 메소드를 갖는다는 것을 나타낸다.

![image](https://user-images.githubusercontent.com/52312640/126433620-b2d15cef-c48d-4eeb-b197-e97f4ef9ca2b.png)

그렇다면 이를 기반 클래스로 하는 파생 클래스들은 각각 자신에게 맞는 필드와 메서드, 그리고 execute() 구현하면 된다.

![image](https://user-images.githubusercontent.com/52312640/126434381-5847c497-5c2d-4a3b-b5e7-1087f7bd5dbb.png)

```java
@SpringBootTest
class HelloTransactionTest {

    @Autowired
    private TransactionFactory transactionFactory;

    @Test
    @DisplayName("커맨드 패턴 테스트")
    void test_command_pattern() {
        Transaction transaction = transactionFactory.creationTransaction("hello");
        transaction.execute();
    }

}

// output
"Hello! I'm Hello Transaction"
```



#### 퍼사드 패턴

###### *Scenario*

급여 관리 프로젝트는 직원을 추가하는 트랜잭션을 수행할 수 있다.

여기서 직원의 데이터는 데이터베이스에 저장이 되는데, 데이터베이스와 연동하기 위해 EmployeeRepository 클래스를 정의하여 사용한다.

> 현재 프로젝트에서는 Spring-Data-JPA를 사용하고 있기 때문에 Entity 클래스를 정의하고, 이 Entity 클래스를 위한 Repository 인터페이스를 정의하면 Spring에서 자동으로 구현 클래스와 그에 맞는 인스턴스를 제공해준다.
>
> 그렇기 때문에 사용할 Repository를 용도에 맞게 CrudRepository, PagingAndSortingRepository, JpaRepository를 상속 받으면 된다.

```java
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    default Employee findOne(int empId) {
        return this
                .findById(empId)
                .orElseThrow(
                        () -> new IllegalArgumentException("Not Found employee: " + empId)
                );
    }
}
```



각각의 트랜잭션은 직원의 데이터를 조회하기 위해 정의한 Repository를 의존하여 사용하면 데이터베이스와의 연동 책임은 Repository에만 부여가 되고 트랜잭션은 Repository에서 조회한 데이터를 사용하면 된다.

```java
public abstract class AddEmployeeTransaction implements Transaction {

    private final Integer empId;
    private final String name;
    private final String address;

    private EmployeeRepository employeeRepository;

    ...

}
```



#### 템플릿 메서드 패턴

###### *Scenario*

직원을 추가할 때 해당 직원이 어떤 방식으로 급여를 받는 지에 따라 트랜잭션이 다르게 동작한다.

- 월급

  월급을 받는 방식이며, 추가적인 성과급은 없고, 급여를 받는 날짜가 월말이다.

- 시간제

  일한 시간에 따라 급여를 받으며, 주에 금요일마다 지급이 된다.

- 판매량에 따른 수수료

  급여가 정해져 있지만, 판매량에 따른 수수료를 받을 수 있으며, 격주마다 지급이 된다.

→ 위와 같은 방식이 나누어져 있지만 공통적으로는 각 방식에 따른 지급하는 방법과 지급받는 날짜가 별도로 정해져 있음을 알 수 있다.

![image](https://user-images.githubusercontent.com/52312640/126434381-5847c497-5c2d-4a3b-b5e7-1087f7bd5dbb.png)

따라서 직원을 추가하는 트랜잭션은 공통된 과정을 거치지만 사용하는 실제 인스턴스는 다르기 때문에 기반 클래스에서 공통된 과정을 템플릿 메서드로 정의하고, 실제 인스턴스를 생성하는 부분을 파생 클래스에서 정의한다.

```java
public abstract class AddEmployeeTransaction implements Transaction {

    ...
    
    // 템플릿 메서드 정의
    @Override
    @Transactional
    public void execute() {
        // template

    }

    // 파생 클래스가 구현해야 할 부분
    public abstract PaymentClassification getClassification();

    public abstract PaymentSchedule getSchedule();

}
```



#### 널 오브젝트 패턴

###### *Scenario*

직원들은 조합에 가입이 되어 있을수도 있고, 아닐수도 있다.

조합에 가입되게 되면, 직원은 급여를 지급 받는 날에 조합에 수수료를 지불하기 때문에 급여가 줄어들어 실제 수령받는 금액과 월급가 차이가 생긴다.

반면에 조합에 가입하지 않은 직원은 수수료가 추가로 발생하지 않기 때문에 실제 수령받는 금액과 월급의 차이가 없다.



조합에 가입된 직원은 Affiliation 인스턴스를 갖게되어 다음과 같은 과정으로 급여를 받게 된다.

```java
public Paycheck payday(Date payDate) {
    Paycheck pc = new Paycheck(getPayPeriodStartDate(payDate), payDate);
    double grossPay = classification.calculatePay(pc);
    double deductions = 0;
    if(this.affiliation != null) {
        deductions = affiliation.calculateDeductions(pc);
    }
    double netPay = grossPay - deductions;
    pc.setGrossPay(grossPay);
    pc.setDeductions(deductions);
    pc.setNetPay(netPay);
    this.method.pay(pc);

    return pc;
}
```



실제 직원이 갖고 있는 Affiliation 인스턴스에 대한 null 검사가 이루어지기 때문에 여기에 널 오브젝트 패턴을 적용한다.

<img src="https://user-images.githubusercontent.com/52312640/126439798-9d087935-1c19-431b-84eb-39a091798b18.png" alt="image" style="zoom:120%;" />

#### 

#### 스트래터지 패턴

###### *Scenario*

직원은 각각 다른 방식으로 급여를 지급받는다는 것을 안다.

시간에 따라, 판매량에 따른 수수료, 그냥 월급을 지급 받는 등 각각은 분류되게 된다.

따라서 이는 직원이 갖고 있는 PaymentClassification과 조합에 속해 있다면 Affiliation의 인스턴스로부터 각각의 급여를 처리하는 방식에 대해 책임을 위임하고 직원 객체는 단지 처리가 끝난 급여와 수수료를 계산하여 급여를 지급받으면 될 뿐이다.

```java
public Paycheck payday(Date payDate) {
    Paycheck pc = new Paycheck(getPayPeriodStartDate(payDate), payDate);
    double grossPay = classification.calculatePay(pc);
    double deductions = affiliation.calculateDeductions(pc);
    double netPay = grossPay - deductions;
    pc.setGrossPay(grossPay);
    pc.setDeductions(deductions);
    pc.setNetPay(netPay);
    this.method.pay(pc);

    return pc;
}
```

