# SPMIS

### STUDENT PROJECT MANAGEMENT INFORMATION SYSTEM ( 학생 프로젝트 관리 정보 시스템 )
- 프로젝트의 관리를 편하게하기 위한 프로젝트 관리 정보 시스템
- TO DO 리스트 칸반형식의 Task들과 외부 회의방을 사용하여 프로젝트를 관리하고 대시보드를 통해 참여중인 프로젝트들의 정보를 확인 할 수있다.

![image](https://user-images.githubusercontent.com/73861946/201593556-16a875ca-17d5-4652-b73f-6ae60792d66b.png)


# 팀원 

### 정재령
#### 기능 개발 
- 프로젝트 배포 및 파이프라인 
- Spring Security와 Oauth2를 사용한 로그인
- 디스코드 웹훅(알림)
- 대시보드
- 회의방 링크 및 웹훅링크 관리
- 공개 프로젝트 
- 그룹원 관리
- 파일 공유 (개발 진행 중)
- 알림기능

### 백진우
#### 기능 개발
- chart  
- 이메일 전송 및 인증
- 칸반 관련 및 태스크
- 프로젝트 리스트
- 문서 변환 모듈(개발 진행 중)
- 간트 차트 및 캘린더 제작(개발 진행 중)

### 공통작업
인원수가 적어 백-프론트로 나눈다기보다는 기능 위주로 나누어서 개발하였습니다.
특히 프론트의 경우 먼저 기본 틀을 만들어 놓고 필요한 기능이 있다면 파트를 맡은 사람이 수정하여 사용하였습니다.

## 기능 
### 사용자 관리(Login, join)
- Security와 Oauth2 기술을 사용하여 Form 로그인 구글계정을 이용한 로그인
- 프로젝트별 사용자 권한관리 (개선중)

### Mypage
- 프로필 관리
- 패스워드 변경
- 프로젝트 참가요청 확인 및 수락, 거절
- 닉네임 변경


## 프로젝트 관리
### Project List
- 참여중인 프로젝트들의 리스트를 확인하고 새로운 프로젝트 생성

### Project
- TO DO 리스트의 칸반 형식으로 작업 진척도 관리
- Task(작업 내용) 생성, 수정, 삭제
- 칸반 생성, 수정, 삭제
- 프로젝트별 룰을 생성, 삭제

### DashBoard
- 본인이 맡은 작업내용, 프로젝트, 프로젝트별 회의방 링크들을 확인

### Project Settings
- 프로젝트 삭제
- 공개범위 수정
- 참여자 별 작업 수 차트로 확인
- 그룹원 추가, 삭제 및 권한 수정
- 회의방 링크 추가 ( 외부 회의방 ex) discord, kakao 오픈 채팅방, 줌 등의 링크)
- 디스코드 웹훅 기능 ( 프로젝트 공지사항을 discord 회의방에 동시에 올려주기 위한 웹훅)

### Public Project List
- 공개된 프로젝트 리스트 확인
- 연결된 프로젝트의 정보를 확인한다.
  (현재는 project 페이지로 바로 이동하지만 새로운 페이지를 하나두어서 참여자와는 다른 화면을 보일 수 있게끔 개선)
- 리스트에 참여버튼으로 프로젝트에 참여요청이 가능 

## 기술 스택
- 개발언어 : Java, JavaScript, HTML, CSS 
- IDE : Intellij
- Framework : Spring Framework, mybaties
- view : Thymeleaf
- DB : MySql(workbentch를 사용하여 관리)
- 배포 : ~Heroku~, koyeb
- CI/CD : GitLab
- 라이브러리 : chart.js
- 협업 도구 : GitLab, GitHub


## 개선할 사항 및 현재 미구현 기능
- Project Setting쪽에 몰려있는 기능들을 페이지 분리하여 적용
- 파일 공유 기능 개발
- 문서 작성 모듈 개발
- 간트 차트 및 캘린더 제작
- 개인 프로필 페이지 제작
- 공개 프로젝트에 접근할때 공개 범위를 지정할때 상세하게 설정하여 보여줄 수 있도록 개선
- 전체적인 디자인 
- Heroku 무료 플랜 종료로인한 배포 및 DB 서비스 이전

## 배포 링크 ( 현재 DB 연결 X)
- https://spmis-rwhisper.koyeb.app/
- ~https://spmis.herokuapp.com/ (현재 헤로쿠 무료 플랜 종료로 인한 배포 중단 중)~ 

### 이슈는 여기 repository에 부탁드립니다
