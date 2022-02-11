const opens = () =>
{
    const modal = document.querySelector("#main_modal");
    modal.classList.remove('hidden');
}
const closes = () =>
{
    const modal = document.querySelector("#main_modal");
    modal.classList.add('hidden');
}
const opens_rejestr = () =>
{
    closes();
    const modal = document.querySelector("#second_modal");
    modal.classList.remove('hidden');
}
const second_closes = () =>
{
    const modal = document.querySelector("#second_modal");
    modal.classList.add('hidden');
}
const rentals_akcept = () =>
{
    closes();
    const modal = document.querySelector("#second_modal");
    modal.classList.remove('hidden');
}

const shows_more = () =>
{
    const tresc = document.querySelector("#books_cards");
    const tresci = tresc.innerHTML;
    const dodatkowe =tresci+'<div class="book_card"><div class="book__card_img"><img src="img/beksinski_obrazy.png"></div>\
    <div class="book__card_info"><div class="title"><span class="titles">Tytuł:</span> Beksiński Obrazy</div>\
    <div class="title"><span class="titles">Autor:</span>	Banach Wiesław , Ochman Wiesław , Pluta Władysław</div><div class="title">\
    <span class="titles">Wydawnictwo:</span> bosh</div><div class="title"><span class="titles">Data premiery:</span> 2021-12-13  </div>\
    </div><div class="rental_button"><button class="button" type="button">Szczegóły!</button> </div></div>';
    tresc.innerHTML = dodatkowe;

}
