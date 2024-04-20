#  <div align="center"> ![fontLogo01](https://github.com/jobdfhjfghfg/happymallSpring/assets/155034269/c32f3765-883a-4980-be63-497ead97d12e) <br> Smile Implant </div>

### <div align="center"> [Smile Implant 바로가기](http://3.34.67.174:8080)</div>
<br>
<div align="center">testId : admin <br>
                    password : test123 </div>
<br>

<details>
<summary>목차</summary>

1. [프로젝트 소개](#intro)
2. [팀원 소개](#team)
3. [기술 및 개발 환경](#dex)
4. [개발 일정](#schedule)
5. [백엔드 구현 기능](#feature)
6. [메인 코드](#main)

</details>
<br>

## <span id="intro">1. 프로젝트 소개</span>
<b> Smile Implant는 생산 공정 관리 시스템을 구축한 임플란트 생산공정 시스템입니다. </b>

* 자동화 생산공정으로 효율적인 재고관리를 할 수 있습니다.
* 에러원인 등을 한눈에 파악할 수 있습니다.
* 파이차트를 통한 에러율 체크를 할 수 있습니다.

<br>
<br>

## <span id="team">2. 팀원 소개</span>

![스크린샷 2024-04-20 222004](https://github.com/jobdfhjfghfg/implant/assets/155034269/609228fe-80df-4fbd-8d7f-8e19e8ffd738)


<br>
<br>

## <span id="dex">3. 기술 및 개발 환경</span>

<div align="center">

| FrontEnd | BackEnd | Design | DB | IDE |
| :----: | :----: | :----: | :----: | :----: |
|  <img src="https://img.shields.io/badge/html5-E34F26.svg?style=flat-square&logo=html5&logoColor=white"> <img src="https://img.shields.io/badge/bootstrap-7952B3?style=flat-square&logo=bootstrap&logoColor=white">   | <img src="https://img.shields.io/badge/springboot-6DB33F?style=flat-square&logo=springboot&logoColor=white"> | <img src="https://img.shields.io/badge/figma-FBCEB1?style=flat-square&logo=figma&logoColor=white"> | <img src="https://img.shields.io/badge/mysql-4479A1?style=flat-square&logo=mysql&logoColor=white"> | <img src="https://img.shields.io/badge/intellijidea-000000?style=flat-square&logo=intellijidea&logoColor=white">  |
</div>

<br>
<br>

## <span id="schedule">4. 개발 일정</span>

<div align="center">
  
| 개발 일정 | 개발 내용 |
| :----: | :----: |
| 2.13 ~ 2.16 <br><hr> 2.19 ~ 2.27 <br><hr> 2.28 ~ 3.11 <br><hr> 3.12 ~ 3.15 <br> | 프로젝트 기획 <br><hr> 도메인 모델 설계 <br><hr> 메인 기능 설계 <br><hr> Trouble Shooting 및 보완 <br>
</div>


## <span id="feature">5. 백엔드 구현 기능</span>

<div align="center">
  
| 기능 목록 | 상세 기능 |
| :----: | :----: |
|  회원 기능 <br><hr>제품 등록 기능<br><br>  <hr><br>출고 기능<br><hr> 공정현황 <br><hr> | 1. 회원가입 <br> 2. 로그인 <br><hr> 1. 원재료 등록 <br> 2. 제품 재고 확인 <br> 3. BOM 등록 및 조회 <br><hr> 1. 제품 출고 <br><hr> 1. 공정별 에러 확인 <br> 2. 에러율 확인 <br><hr>
</div>


## <span id="main">6. 메인 코드 </span>

* 장바구니 추가 addCart 코드

```
if (cart == null) {
            cart = Cart.createCart(member1);
            cartRepository.save(cart);
        }
```
=> cart에 정보가 없으면 새 cart 생성

<br>

```
if (cartItem == null) {
            cartItem = CartItem.createCartItem(cart, inventory, count);
            cartInventoryRepository.save(cartItem); }
``` 
=> 장바구니에 정보가 없으면 새 장바구니를 만들어줌

<br>

```
else {
            CartItem updateCartItem = cartItem;
            updateCartItem.setCart(cartItem.getCart());
            updateCartItem.setInventory(cartItem.getInventory());
            updateCartItem.setCount(cart.getCount() + count);
            updateCartItem.setPrice(inventory.getPrice() * (cart.getCount() + count));

            cartInventoryRepository.save(updateCartItem);
        }
```
=> 장바구니에 정보가 있으면 update시켜줌

<br>
<br>
<br>

* 카카오 API 우편번호 검색

```
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script>
  const btn = document.querySelector("#btn")
  btn.addEventListener("click", () => {
    new daum.Postcode({
      oncomplete: function(data) {
        
        let fullAddr = '';
        let extraAddr = '';

        if (data.userSelectedType === 'R') { //도로명주소인 경우
          fullAddr = data.roadAddress;
        } else {
          fullAddr = data.jibunAddress; //지번주소인 경우
        }

        if (data.userSelectedType === 'R') {

          if (data.bname !== '') {
            extraAddr += data.bname;
          }

          if (data.buildingName !== '') {
            extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName); //법정동주소가 비어있지 않으면 빌딩명이랑 붙여서 쓰고 아니면 빌딩명만 쓰기
          }

          fullAddr += (extraAddr !== '' ? '(' + extraAddr + ')' : '');

        } else {
          document.getElementById("extraAddr").value = '';
        }

        document.getElementById('zipcode').value = data.zonecode;
        document.getElementById("fullAddr").value = fullAddr;
        document.getElementById("extraAddr").focus();
      }
    }).open();
  });

</script>
```

==> 카카오 API를 이용한 우편번호 검색

외부 API를 이용한 주소검색으로 우편번호, 주소 등을 간단히 입력가능하게끔 구현함.
