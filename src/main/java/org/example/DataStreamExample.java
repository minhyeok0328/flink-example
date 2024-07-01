package org.example;

import org.apache.flink.api.common.JobExecutionResult;
import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class DataStreamExample {

    public static void main(String[] args) throws Exception {
        // Flink 스트리밍 실행 환경을 생성
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // Person 객체의 스트림을 생성
        DataStream<Person> personStream = env.fromElements(
            new Person("Alice", 25),   // Person 객체 생성 (이름: Alice, 나이: 25)
            new Person("Bob", 30),     // Person 객체 생성 (이름: Bob, 나이: 30)
            new Person("Charlie", 3)   // Person 객체 생성 (이름: Charlie, 나이: 3)
        );

        // 성인(Person 객체의 나이가 19세 이상인)을 필터링하는 스트림을 생성
        DataStream<Person> adultStream = personStream.filter(new FilterFunction<Person>() {
            @Override
            public boolean filter(Person person) {
                return person.age > 19; // 나이가 19세 초과인 경우 true 반환
            }
        });

        // 필터링된 성인 스트림을 출력
        adultStream.print();
        // 스트리밍 실행
        env.execute();
    }

    // Person 클래스 정의
    public static class Person {
        public String name;
        public Integer age;

        // 기본 생성자
        public Person() {}

        // 이름과 나이를 받아들이는 생성자
        public Person(String name, Integer age) {
            this.name = name;
            this.age = age;
        }

        // Person 객체의 문자열 표현을 반환하는 메소드
        @Override
        public String toString() {
            return this.name + ": " + this.age.toString();
        }
    }

}
