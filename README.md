## 설계

### v1 - 온디맨드 피드

- 사용자 요청이 있을 때마다 피드를 생성한다
    1. 사용자 A의 팔로우 목록(팔로잉)을 가져온다
    2. 팔로우하는 사용자(팔로잉)들의 게시물들을 가져온다
    3. 게시물들을 모아 피드로 보여준다
- 단점: 쿼리가 복잡하며 조회 비용이 크다
    - 시간복잡도 ↑, (팔로잉 수 * 팔로우하는 사용자의 게시물 수)에 지수적 영향을 받는다

### v2 - 사전 생성된 피드

- 사용자의 피드를 미리 생성해두고, 팔로우하는 사용자의 새 게시물을 피드에 추가한다
    1. 사용자 A는 사용자 B를 팔로우한다
    2. 사용자 B가 게시물을 작성하면 팔로워(A)들의 피드에 해당 게시물을 삽입한다
    3. 사용자 A가 피드를 조회한다
- 장점: 피드 조회 비용이 저렴하다
    - 시간복잡도 ↓, 팔로잉 수 및 게시물 수에 영향받지 않는다
- 단점:
    - 게시물에 대한 쓰기 성능이 감소한다
    - 비동기 작업 시 피드 반영이 늦을 수 있다
    - 포스팅 5만개 사용자 1만명이라 가정하자
        - 사용자마다 팔로우를 100명씩 한다고 하더라도 Feed 테이블 컬럼 수가 최소 10만은 넘을 것이다
        - 따라서 DB 성능 상 무리가 있을 것이다 → DB index를 사용하면 무리가 줄어들 것이다

### v3 - 비동기 피드 생성

- 게시물에 대한 쓰기 성능이 감소한다 → 비동기 작업으로 극복할 수 있다
- 비동기 작업 시 피드 반영이 늦을 수 있다 → 비즈니스 특성 상 실시간성이 중요하지는 않으며, 사용자는 체감이 어려울 것이다

### v4 - 사전 생성된 피드 캐싱

- 인메모리 DB(redis)를 사용해서 최적화를 해보자

---

## 도메인

### 게시물

- 제목, 내용을 포함한다
- 어떤 사용자의 게시물인지 알 수 있다

### 피드

- 여러 개의 피드 게시물을 포함한다

### 사용자

- 이름을 포함한다
- 사용자 유형을 분류한다

### 팔로우

- 다른 사용자를 팔로우 할 수 있다
- 팔로우 여부를 알 수 있다
- 팔로워들을 알 수 있다
- 주인을 알 수 있다
- 자신을 팔로우 할 수 없다

---

## 서비스

### 팔로우 서비스

- 주체와 대상이 모두 존재해야 한다
- 다른 사용자를 팔로우 할 수 있다
- 어떤 사용자의 팔로워들을 알 수 있다

### 피드 서비스

- 사용자의 피드를 조회한다
- 팔로우하는 사용자들의 게시물을 포함해야 한다

---

## 페어 프로그래밍 규칙

![image](https://github.com/user-attachments/assets/c2a62d93-f1cb-4f94-9974-278493d802e5)

1. 연속 2시간 이상 페어 프로그래밍 금지
2. 네비게이터(김정은)은 자신의 의도를 말한다
3. 드라이버는 의도에 맞춰 코드를 자율적으로 작성한다
4. 드라이버가 잘못하고 있을 때도 일단 기다려본다
   > **Impatience**
   >
   >
   > Apply the “5 seconds rule”: When the navigator sees the driver do something “wrong” and wants to comment, wait at
   least 5 seconds before you say something - the driver might already have it in mind, then you are needlessly
   interrupting their flow.
5. 너무 작은 단위로 네비게이팅 하지 않는다
   > **Micro-Management Mode**
   >
   >
   > Watch out for micro-management mode: It doesn't leave room for the other person to think and is a frustrating
   experience, if someone keeps giving you instructions like:
   >
   > - “Now type 'System, dot, print, “...
   > - “Now we need to create a new class called...”
   > - “Press command shift O...”