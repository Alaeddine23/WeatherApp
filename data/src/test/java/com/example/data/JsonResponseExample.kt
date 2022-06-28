package com.example.data

class JsonResponseExample {
    companion object {
        const val jsonString = """{
  "cod": "200",
  "message": 0.045,
  "cnt": 40,
  "list": [
    {
      "dt": 1506880800,
      "main": {
        "temp": 14.76,
        "temp_min": 14.64,
        "temp_max": 14.76,
        "pressure": 1020.13,
        "sea_level": 1032.15,
        "grnd_level": 1020.13,
        "humidity": 96,
        "temp_kf": 0.12
      },
      "weather": [
        {
          "id": 500,
          "main": "Rain",
          "description": "light rain",
          "icon": "10n"
        }
      ],
      "clouds": {
        "all": 92
      },
      "wind": {
        "speed": 5.21,
        "deg": 199.505
      },
      "rain": {
        "3h": 0.96
      },
      "sys": {
        "pod": "n"
      },
      "dt_txt": "2022-06-27 18:00:00"
    }
  ],
  "city": {
    "id": 2988507,
    "name": "Paris",
    "coord": {
      "lat": 48.8534,
      "lon": 2.3488
    },
    "country": "FR"
  }
}"""
    }
}