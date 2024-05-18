# 스프링 입문주차 개인과제

## 📍 프로젝트 소개

"나만의 일정 관리 앱 서버 만들기"

## 📍 Use Case Diagram

<img src ="https://github.com/llocr/NBCamp-Spring-Schedule/assets/114149212/ffaa450d-12cc-4487-8bf1-468160017e5e" width=500>

## 📍 API 명세서

| 기능        | Method | URL                  | request                                                                                                      | response                                                                                                                                                                                                                                                                                                                                                                                                                                                                |
|-----------|--------|----------------------|--------------------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| 일정 등록     | POST   | `/api/schedule`      | `{ "title": "test", "contents": "hello", "email": "heesue@gmail.com", "password": "1234" }`         | `{"statusCode": 201,"message": "일정이 성공적으로 추가되었습니다.","data": {"id": 16,"title": "test","contents": "hello","email": "heesue@gmail.com","createDate": "2024-05-17T16:47:04.091232","modifyDate": "2024-05-17T16:47:04.091232"}}`                                                                                                                                                                                                                                          |
| 선택한 일정 조회 | GET    | `/api/schedule/{id}` |                                                                                                              | `{"statusCode" 200,"message": "선택한 일정 조회가 완료되었습니다.","data": {"id": 2,"title": "수정된 테스트입니다.","contents": "오늘의 스케줄은 무엇일까요?","email": "heesue@gmail.com","createDate":"2024-05-15T18:38:12.061743","modifyDate": "2024-05-17T16:18:59.292645"}}`                                                                                                                                                                                                                           |
| 전체 일정 조회  | GET    | `/api/shcedule`      |                                                                                                              | `{"statusCode":200,"message": "목록 조회가 완료되었습니다.","data": [{"id": 16,"title": "test","contents": "hello","email": "heesue@gmail.com","createDate": "2024-05-17T16:47:04.091232","modifyDate": "2024-05-17T16:47:04.091232"},{"id": 2,"title": "수정된 테스트입니다.","contents": "오늘의 스케줄은 무엇일까요?","email": "heesue@gmailcom","createDate": "2024-05-15T18:38:12.061743","modifyDate": "2024-05-17T16:18:59.292645"}]}`                                                            |
| 선택한 일정 수정 | PUT    | `/api/schedule/{id}` | `{ "title": "수정된 테스트입니다!", "contents": "오늘의 스케줄은 무엇일까요?", "email": "heesue@gmail.com", "password": "1234" }` | `{"statusCode": 200,"message": "선택한 일정 수정이 완료되었습니다.","data": {"id": 2,"title": "수정된 테스트입니다.","contents": "오늘의 스케줄은 무엇일까요?","email": "heesue@gmail.com","createDate": "2024-05-15T18:38:12.061743","modifyDate": "2024-05-17T16:18:59.292645"}}`                                                                                                                                                                                                                         |
| 선택한 일정 삭제 | DELETE | `/api/schedule/{id}` | `{ "password": "1234" }`                                                                                     | `{"statusCode" 200,"message": "선택한 일정 삭제가 완료되었습니다.","data": 2}`                                                                                                                                                                                                                                                                                                                                                                                                         |
| 파일 업로드    | POST   | `/api/files`         | **Body - [form-data]** <br> files(File) : “증명1.jpg”, <br> description(Text) : “사진입니다”                        | `{"statusCode": 200,"message": "파일 업로드가 완료되었습니다.","data": [{"originalFileName": "증명1.jpg","savedName": "4b5e10aa-8066-45ff-a626-0bb0c9f8c22b_증명1.jpg","filePath": "./files/4b5e10aa-8066-45ff-a626-0bb0c9f8c22b_증명1.jpg","description": "사진에 대한 설명입니다"},{"originalFileName": "증명2.jpg","savedName": "0b4da3a2-31e6-41f3-a2f7-95a197249a85_증명2.jpg","filePath": "./files/0b4da3a2-31e6-41f3-a2f7-95a197249a85_증명2.jpg","description": "사진에 대한 설명입니다"}]}` |

**[POSTMAN으로 보기]** : https://documenter.getpostman.com/view/28179041/2sA3JT4eQP

**[노션으로 보기]** : https://369696.notion.site/8b24dfdf2cc44f0daf074af9c4d0b4a0?v=fb59d80dd1754bfabecdc1abfbf17920&pvs=4

## 📍 ERD

<img src ="https://github.com/llocr/NBCamp-Spring-Schedule/assets/114149212/d51194e3-f528-4ddb-9b04-4d2b02d02da8" width=500>
