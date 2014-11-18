
def calc(amount: Double, iloscRat: Int) = {
  //  val percent = 0.12
  val percent = 0.04467
  val iloscRatWOkresieOprocentowania = 12
  val q = 1 + (percent / iloscRatWOkresieOprocentowania)
  val odsetki = amount * (percent / iloscRatWOkresieOprocentowania)

  val rata: Double = amount * Math.pow(q, iloscRat) * (q - 1) / (Math.pow(q, iloscRat) - 1)
  val splataSalda: Double = rata - odsetki
  (rata, splataSalda, odsetki)
}

val splaconeTotal = 8107.36
val OBECNA_NADPLATA = 100 + 1000 + 2300 + 1000 + 2990 //+ 24000
val KWOTA_KREDYTU = 138857 - splaconeTotal - 24000

// obecna rata 664,37 EUR

def obliczKosztIOkres(saldoDlugu: Double, iloscRatDoKonca: Int, nadplata: Int, kosztCalkowity: Double): (Double, Double) = {
  val rata = calc(saldoDlugu, iloscRatDoKonca)

  if (rata._1 < 0) sys.error(s"rata ujemna $rata , saldo=$saldoDlugu")
  if (iloscRatDoKonca < 0) sys.error("WTF " + rata)

  val noweSalod: Double = saldoDlugu - rata._2 - nadplata
  val koszt: Double = kosztCalkowity + rata._1 + nadplata + noweSalod

  if (noweSalod <= 0) {
    println(s"Rata=$rata - Miesiecy pozostalo=${iloscRatDoKonca - 1}")
    println("saldo < 0")
    (iloscRatDoKonca, koszt)
  } else if (iloscRatDoKonca > 0) {
    if (iloscRatDoKonca % 12 == 0 || iloscRatDoKonca < 5)
      println(s"Rata=$rata - Miesiecy pozostalo=${iloscRatDoKonca - 1}")

    obliczKosztIOkres(noweSalod, iloscRatDoKonca - 1, nadplata, kosztCalkowity + rata._1 + nadplata)
  }
  else
    (iloscRatDoKonca, koszt)
}

val okres30: Int = 12 * 30
val na30Lat = obliczKosztIOkres(KWOTA_KREDYTU, okres30, 1260, 0)
s"Czas splaty ${okres30 - na30Lat._1}miesiecy czyli ${(okres30 - na30Lat._1)/ 12}lat"


val okres10: Int = 12 * 10
val na10Lat = obliczKosztIOkres(KWOTA_KREDYTU, okres10, 0, 0)
s"Czas splaty ${okres10 - na10Lat._1}miesiecy czyli ${(okres10 - na10Lat._1)/ 12}lat"


s"KOSZT KREDYTU NA $KWOTA_KREDYTU NA 10 lat = " + (na10Lat._2.toInt - KWOTA_KREDYTU)
s"KOSZT KREDYTU NA $KWOTA_KREDYTU NA 30 lat = " + (na30Lat._2.toInt - KWOTA_KREDYTU)