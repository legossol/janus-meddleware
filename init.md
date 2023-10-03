# 프로젝트 초기화

### 1. 템플릿 프로젝트로 신규 프로젝트 생성
- Get from VCSD ( 또는 File -> New -> Project From Version Control...)

### 2. Template Git 을 신규 프로젝트로 Clone
- 1. 깃주소
- 2. 신규 프로젝트 명으로 디렉토리 생성

### 3. Project 이름 변경
- 1 : [settings.gradle](../settings.gradle) 파일 열기
- 2 : 'rootProject.name'의 값을 신규 프로젝트명 (artifact-id)로 변경
- 3(선택) : Gradle Fresh 버튼 클릭 -> 프로젝트명 변경 확인

### 4. 패키지명 변경
- [src/main/java/kr/legossol/sample](src/main/java/kr/legossol/basicProjectTemplate) 우클릭 - Refactor -> rename
- 'All Directories' 선택 -> 기본 패키지명 변경 -> Refactor

### 5. SampleApplication 이름 변경
- [프로젝트명]Application 으로 변경

### 6. 기존 Git(Template) 제거
- 프로젝트 Root 우클릭 -> Open in -> Terminal
```aidl
rm -rf ./git
```

- Preferences... -> Version Control -> Directory Mappings 에서 '<Project>' 제거 후 'OK'
### 7. Git 재생성
- VCS -> Enable Version Control Integration... -> Git -> 'OK'
- > VCS 메뉴는 Git 정보가 없어야 활성화 된다.

- (선택) Git -> Manage Remotes... -> 신규 프로젝트 Git URL을 'origin' 으로 추가
- > Enable Version Control Integration 아ㅣ 완료되면 VCS 메뉴는 사라지고, Git 으로 변경 된다.


### 8.설정 파일 수정
> properties 형태로 key를 표시하였음
- [application.yml](src/main/resources/application.yml)
    - spring.application.name : 신규 프로젝트 명으로 변경
    - spring.datasource.url : URL 확인 및 스키마명 변경
    - spring.cloud.stream : kafka 사용 여부에 맞게 변경
    - server.port : 기본 포트 8080변경 필요시 변경
    - server.servlet.context-path : 신규 프로젝트에 맞게 변경
    - cache : 불필요시 'enable'을 'false'로 변경
- [application-dev.yml](src/main/resources/application-dev.yml)
    - spring.datasource.url : URL 확인 및 스키마명 변경
    - spring.datasource.hikari.data-source-properties.v$session.program : 명칭 변경
    - spring.cloud.stream : kafka 사용 여부에 맞게 변경
    - server.port : 개발 환경 포트로 변경
- [application-stage.yml](src/main/resources/application-stage.yml)
    - spring.datasource.url : URL 확인 및 스키마명 변경
    - spring.datasource.hikari.data-source-properties.v$session.program : 명칭 변경
    - spring.cloud.stream : kafka 사용 여부에 맞게 변경
    - server.port : 개발 환경 포트로 변경
- [application-prod.yml](src/main/resources/application-prod.yml)
    - spring.datasource.url : URL 확인 및 스키마명 변경
    - spring.datasource.hikari.data-source-properties.v$session.program : 명칭 변경
    - spring.cloud.stream : kafka 사용 여부에 맞게 변경
    - server.port : 개발 환경 포트로 변경
- [properties/swagger/swagger.yml](src/main/resources/properties/swagger/swagger.yml)
    - springdoc.info.title
    - springdoc.info.description
    - springdoc.info.version
    - springdoc.group-configs : AP*I URL 에 따른 그룹 선언