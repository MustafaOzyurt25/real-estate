

CREATE TABLE IF NOT EXISTS countries (
  id int NOT NULL,
  name varchar(80) NOT NULL,
  PRIMARY KEY(id)
)  ;
--
--

INSERT INTO countries (id, name) VALUES
(1, 'AFGHANISTAN'),
(2, 'ALBANIA'),
(3, 'ALGERIA'),
(4, 'AMERICAN SAMOA'),
(5, 'ANDORRA'),
(6, 'ANGOLA'),
(7, 'ANGUILLA'),
(8, 'ANTARCTICA'),
(9, 'ANTIGUA AND BARBUDA'),
(10, 'ARGENTINA'),
(11, 'ARMENIA'),
(12, 'ARUBA'),
(13, 'AUSTRALIA'),
(14, 'AUSTRIA'),
(15, 'AZERBAIJAN'),
(16, 'BAHAMAS'),
(17, 'BAHRAIN'),
(18, 'BANGLADESH'),
(19, 'BARBADOS'),
(20, 'BELARUS'),
(21, 'BELGIUM'),
(22, 'BELIZE'),
(23, 'BENIN'),
(24, 'BERMUDA'),
(25, 'BHUTAN'),
(26, 'BOLIVIA'),
(27, 'BOSNIA AND HERZEGOVINA'),
(28, 'BOTSWANA'),
(29, 'BOUVET ISLAND'),
(30, 'BRAZIL'),
(31, 'BULGARIA'),
(32, 'BURKINA FASO'),
(33, 'BURUNDI'),
(34, 'CAMBODIA'),
(35, 'CAMEROON'),
(36, 'CANADA'),
(37, 'CAPE VERDE'),
(38, 'CAYMAN ISLANDS'),
(39,'TURKEY');



CREATE TABLE IF NOT EXISTS cities (
  id int NOT NULL,
  name varchar(80) NOT NULL,
  country_id int NOT NULL,
  PRIMARY KEY(id)
)  ;
--
--
INSERT INTO cities (id, name, country_id) VALUES

(1, 'ADANA',39),
(2, 'ADIYAMAN',39),
(3, 'AFYON',39),
(4, 'AGRI',39),
(5, 'AMASYA',39),
(6, 'ANKARA',39),
(7 , 'Antalya' , 39),
(34, 'ISTANBUL',39);





CREATE TABLE IF NOT EXISTS districts (
  id int NOT NULL,
  name varchar(80) NOT NULL,
  city_id int NOT NULL,
  PRIMARY KEY(id)
)  ;

INSERT INTO districts (id, name, city_id) VALUES

(1, 'KADIKOY',34),
(2, 'BESIKTAS',34),
(3, 'EYUP',34),
(4, 'PENDIK',34),
(5, 'MALTAPE',34),
(6, 'USKUDAR',34);