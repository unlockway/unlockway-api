CREATE TABLE tb_ingredients (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(255) NOT NULL,
    description TEXT,
    calories DOUBLE PRECISION,
    proteins DOUBLE PRECISION,
    water DOUBLE PRECISION,
    minerals VARCHAR(255),
    vitamins VARCHAR(255),
    measure VARCHAR(50),
    fats DOUBLE PRECISION,
    photo VARCHAR(255)
);
INSERT INTO tb_ingredients (
        name,
        description,
        calories,
        proteins,
        water,
        minerals,
        vitamins,
        fats,
        measure,
        photo
    )
VALUES (
        'Iogurte',
        'O iogurte é um produto lácteo fermentado, conhecido por sua textura cremosa e sabor levemente ácido. É produzido pela fermentação bacteriana do leite.',
        59.0,
        3.5,
        88.0,
        'Cálcio, Fósforo',
        'B12,D',
        3.3,
        'AMOUNT',
        'https://unlockwaystaticfiles.blob.core.windows.net/ingredients/yogurt.png'
    );
INSERT INTO tb_ingredients (
        name,
        description,
        calories,
        proteins,
        water,
        minerals,
        vitamins,
        fats,
        measure,
        photo
    )
VALUES (
        'Arroz',
        'O arroz é um cereal amplamente consumido como uma importante fonte de carboidratos na dieta humana. Existem diversas variedades de arroz, sendo um alimento básico em muitas culturas.',
        130.0,
        2.7,
        12.0,
        'Ferro, Magnésio, Fósforo',
        'B1,B3',
        0.3,
        'GRAMS',
        'https://unlockwaystaticfiles.blob.core.windows.net/ingredients/rice.png'
    );
INSERT INTO tb_ingredients (
        name,
        description,
        calories,
        proteins,
        water,
        minerals,
        vitamins,
        fats,
        measure,
        photo
    )
VALUES (
        'Atum',
        'O atum é um peixe popularmente consumido em todo o mundo. É conhecido por ser uma excelente fonte de proteína, ácidos graxos ômega-3 e outros nutrientes essenciais.',
        109.0,
        25.0,
        68.0,
        'Ferro, Magnésio, Fósforo, Potássio, Selênio',
        'B3,B6,B12,D',
        1.0,
        'AMOUNT',
        'https://unlockwaystaticfiles.blob.core.windows.net/ingredients/atum.png'
    );
INSERT INTO tb_ingredients (
        name,
        description,
        calories,
        proteins,
        water,
        minerals,
        vitamins,
        fats,
        measure,
        photo
    )
VALUES (
        'Macarrão',
        'O macarrão é um alimento feito geralmente a partir de trigo, água e, por vezes, ovos. É um dos alimentos básicos em muitas cozinhas ao redor do mundo.',
        131.0,
        3.0,
        70.0,
        'Ferro, Magnésio, Fósforo',
        'B1,B3',
        1.0,
        'GRAMS',
        'https://unlockwaystaticfiles.blob.core.windows.net/ingredients/noodle.png'
    );
INSERT INTO tb_ingredients (
        name,
        description,
        calories,
        proteins,
        water,
        minerals,
        vitamins,
        fats,
        measure,
        photo
    )
VALUES (
        'Feijão Preto',
        'O feijão preto é uma variedade de feijão amplamente consumida em várias culinárias ao redor do mundo. É conhecido por sua cor escura e sabor característico.',
        339.0,
        21.6,
        11.0,
        'Ferro, Magnésio, Fósforo, Potássio',
        'B1,B6',
        1.4,
        'GRAMS',
        'https://unlockwaystaticfiles.blob.core.windows.net/ingredients/black_beans.png'
    );
INSERT INTO tb_ingredients (
        name,
        description,
        calories,
        proteins,
        water,
        minerals,
        vitamins,
        fats,
        measure,
        photo
    )
VALUES (
        'Feijão Carioca',
        'O feijão carioca, também conhecido como feijão-mulatinho, é uma variedade de feijão muito consumida no Brasil. Ele possui grãos com cores que variam entre bege e marrom claro.',
        337.0,
        21.0,
        12.0,
        'Ferro, Magnésio, Fósforo, Potássio',
        'B1,B6',
        1.2,
        'GRAMS',
        'https://unlockwaystaticfiles.blob.core.windows.net/ingredients/beans.png'
    );
INSERT INTO tb_ingredients (
        name,
        description,
        calories,
        proteins,
        water,
        minerals,
        vitamins,
        fats,
        measure,
        photo
    )
VALUES (
        'Lentilhas',
        'As lentilhas são leguminosas comestíveis, conhecidas por sua forma de pequenas lentes e por serem uma excelente fonte de proteínas vegetais, fibras e diversos nutrientes.',
        116.0,
        9.0,
        25.0,
        'Ferro, Magnésio, Fósforo, Potássio',
        'B1,B6',
        0.4,
        'GRAMS',
        'https://unlockwaystaticfiles.blob.core.windows.net/ingredients/lentils.png'
    );
INSERT INTO tb_ingredients (
        name,
        description,
        calories,
        proteins,
        water,
        minerals,
        vitamins,
        fats,
        measure,
        photo
    )
VALUES (
        'Maçã',
        'A maçã é uma fruta de casca fina, polpa suculenta e sabor doce ou levemente ácido. É uma das frutas mais consumidas no mundo, conhecida por seus benefícios à saúde.',
        52.0,
        0.3,
        86.0,
        'Potássio',
        'C',
        0.2,
        'AMOUNT',
        'https://unlockwaystaticfiles.blob.core.windows.net/ingredients/apple.png'
    );
INSERT INTO tb_ingredients (
        name,
        description,
        calories,
        proteins,
        water,
        minerals,
        vitamins,
        fats,
        measure,
        photo
    )
VALUES (
        'Bife',
        'O bife é uma porção de carne bovina, geralmente preparada por meio de cozimento, grelhamento ou fritura. É uma fonte rica de proteínas e nutrientes essenciais.',
        250.0,
        26.0,
        60.0,
        'Ferro, Zinco, Fósforo',
        'B3,B6,B12',
        17.0,
        'AMOUNT',
        'https://unlockwaystaticfiles.blob.core.windows.net/ingredients/beef.png'
    );
INSERT INTO tb_ingredients (
        name,
        description,
        calories,
        proteins,
        water,
        minerals,
        vitamins,
        fats,
        measure,
        photo
    )
VALUES (
        'Linguiça',
        'A linguiça é um embutido de carne moída e temperada, frequentemente defumado ou curado. Ela é utilizada em várias culinárias ao redor do mundo para adicionar sabor a pratos.',
        300.0,
        12.0,
        45.0,
        'Ferro, Fósforo, Zinco',
        'B3,B6,B12',
        25.0,
        'AMOUNT',
        'https://unlockwaystaticfiles.blob.core.windows.net/ingredients/sausage.png'
    );
INSERT INTO tb_ingredients (
        name,
        description,
        calories,
        proteins,
        water,
        minerals,
        vitamins,
        fats,
        measure,
        photo
    )
VALUES (
        'Salsicha',
        'A salsicha é um produto de carne moída, geralmente envolta em uma capa de tripa ou material sintético. Ela pode ser cozida, grelhada ou defumada e é amplamente utilizada em diversos pratos.',
        301.0,
        13.0,
        52.0,
        'Ferro, Fósforo, Zinco',
        'B3,B6,B12',
        26.0,
        'AMOUNT',
        'https://unlockwaystaticfiles.blob.core.windows.net/ingredients/sausage_2.png'
    );
INSERT INTO tb_ingredients (
        name,
        description,
        calories,
        proteins,
        water,
        minerals,
        vitamins,
        fats,
        measure,
        photo
    )
VALUES (
        'Coxa de Frango',
        'A coxa de frango é uma parte da ave que inclui a carne da coxa e, muitas vezes, parte da canela. É uma fonte popular de proteínas e nutrientes essenciais.',
        172.0,
        21.0,
        65.0,
        'Ferro, Fósforo, Potássio',
        'B3,B6,B12',
        9.3,
        'AMOUNT',
        'https://unlockwaystaticfiles.blob.core.windows.net/ingredients/chicken_thigh.png'
    );
INSERT INTO tb_ingredients (
        name,
        description,
        calories,
        proteins,
        water,
        minerals,
        vitamins,
        fats,
        measure,
        photo
    )
VALUES (
        'Filé de Frango',
        'O filé de frango é uma porção de carne de peito de frango, frequentemente utilizado em várias receitas culinárias. É uma fonte magra de proteínas e nutrientes essenciais.',
        165.0,
        31.0,
        69.0,
        'Ferro, Fósforo, Potássio',
        'B3,B6,B12',
        3.6,
        'AMOUNT',
        'https://unlockwaystaticfiles.blob.core.windows.net/ingredients/chicken_fillet.png'
    );
INSERT INTO tb_ingredients (
        name,
        description,
        calories,
        proteins,
        water,
        minerals,
        vitamins,
        fats,
        measure,
        photo
    )
VALUES (
        'Banana',
        'A banana é uma fruta de polpa doce e sabor suave, amplamente consumida em todo o mundo. É uma excelente fonte de energia e nutrientes essenciais.',
        89.0,
        1.1,
        74.0,
        'Potássio, Magnésio, Fósforo',
        'C,B6',
        0.3,
        'AMOUNT',
        'https://unlockwaystaticfiles.blob.core.windows.net/ingredients/banana.png'
    );
INSERT INTO tb_ingredients (
        name,
        description,
        calories,
        proteins,
        water,
        minerals,
        vitamins,
        fats,
        measure,
        photo
    )
VALUES (
        'Melancia',
        'A melancia é uma fruta refrescante e suculenta, conhecida por sua polpa vermelha e sabor doce. É uma excelente fonte de hidratação e nutrientes.',
        30.0,
        0.6,
        91.5,
        'Cálcio, Magnésio, Fósforo',
        'A,C',
        0.2,
        'AMOUNT',
        'https://unlockwaystaticfiles.blob.core.windows.net/ingredients/watermelon.png'
    );
INSERT INTO tb_ingredients (
        name,
        description,
        calories,
        proteins,
        water,
        minerals,
        vitamins,
        fats,
        measure,
        photo
    )
VALUES (
        'Mamão',
        'O mamão é uma fruta tropical conhecida por sua polpa doce e alaranjada. É amplamente consumido por seu sabor e benefícios à saúde.',
        43.0,
        0.5,
        88.0,
        'Cálcio, Magnésio, Fósforo',
        'A,C',
        0.3,
        'AMOUNT',
        'https://unlockwaystaticfiles.blob.core.windows.net/ingredients/papaya.png'
    );
INSERT INTO tb_ingredients (
        name,
        description,
        calories,
        proteins,
        water,
        minerals,
        vitamins,
        fats,
        measure,
        photo
    )
VALUES (
        'Repolho',
        'O repolho é um vegetal crucífero, conhecido por suas folhas compactas e crocantes. É amplamente utilizado em saladas, sopas e outros pratos.',
        25.0,
        1.3,
        92.0,
        'Cálcio, Magnésio, Fósforo',
        'C,K',
        0.1,
        'AMOUNT',
        'https://unlockwaystaticfiles.blob.core.windows.net/ingredients/cabbage.png'
    );
INSERT INTO tb_ingredients (
        name,
        description,
        calories,
        proteins,
        water,
        minerals,
        vitamins,
        fats,
        measure,
        photo
    )
VALUES (
        'Cenoura',
        'A cenoura é um vegetal de raiz, conhecido por sua cor laranja brilhante e sabor doce. É uma excelente fonte de vitamina A e outros nutrientes.',
        41.0,
        0.9,
        88.0,
        'Cálcio, Magnésio, Fósforo',
        'A,C,K',
        0.2,
        'AMOUNT',
        'https://unlockwaystaticfiles.blob.core.windows.net/ingredients/carrot.png'
    );
INSERT INTO tb_ingredients (
        name,
        description,
        calories,
        proteins,
        water,
        minerals,
        vitamins,
        fats,
        measure,
        photo
    )
VALUES (
        'Beterraba',
        'A beterraba é uma raiz vegetal conhecida por sua cor vermelha profunda e sabor terroso. É consumida crua, cozida ou em sucos, e é rica em nutrientes essenciais.',
        43.0,
        1.6,
        87.0,
        'Cálcio, Magnésio, Fósforo',
        'A,C,K',
        0.2,
        'AMOUNT',
        'https://unlockwaystaticfiles.blob.core.windows.net/ingredients/beet.png'
    );
INSERT INTO tb_ingredients (
        name,
        description,
        calories,
        proteins,
        water,
        minerals,
        vitamins,
        fats,
        measure,
        photo
    )
VALUES (
        'Batata',
        'Um vegetal pertencente à família Solanaceae e ao gênero Solanum. As batatas são cultivadas em todo o mundo e são uma parte essencial da alimentação em muitas culturas. As batatas podem ser preparadas de diversas maneiras, como cozidas, assadas, fritas, purê ou até mesmo transformadas em pratos mais elaborados, como gnocchi e batatas gratinadas. Elas desempenham um papel importante na culinária de muitas regiões e são um acompanhamento comum para uma variedade de pratos.',
        77.0,
        2.0,
        79.0,
        'Ferro, Magnésio, Fósforo, Potássio',
        'C,B6',
        0.2,
        'AMOUNT',
        'https://unlockwaystaticfiles.blob.core.windows.net/ingredients/potatos.png'
    );
INSERT INTO tb_ingredients (
        name,
        description,
        calories,
        proteins,
        water,
        minerals,
        vitamins,
        fats,
        measure,
        photo
    )
VALUES (
        'Espinafre',
        'O espinafre é uma folha verde escura comestível e é conhecido por ser uma excelente fonte de nutrientes essenciais. Ele é frequentemente consumido cozido ou cru em saladas.',
        23.0,
        2.9,
        91.4,
        'Ferro, Magnésio, Cálcio, Potássio',
        'A,C,K',
        0.4,
        'AMOUNT',
        'https://unlockwaystaticfiles.blob.core.windows.net/ingredients/spinach.png'
    );
INSERT INTO tb_ingredients (
        name,
        description,
        calories,
        proteins,
        water,
        minerals,
        vitamins,
        fats,
        measure,
        photo
    )
VALUES (
        'Tomate',
        'O tomate é um fruto vermelho comestível, frequentemente utilizado como vegetal em várias preparações culinárias. É uma fonte rica de antioxidantes e vitaminas.',
        18.0,
        0.9,
        94.5,
        'Potássio, Fósforo',
        'C,K',
        0.2,
        'AMOUNT',
        'https://unlockwaystaticfiles.blob.core.windows.net/ingredients/tomato.png'
    );
INSERT INTO tb_ingredients (
        name,
        description,
        calories,
        proteins,
        water,
        minerals,
        vitamins,
        fats,
        measure,
        photo
    )
VALUES (
        'Leite Desnatado',
        'O leite desnatado é uma versão do leite que passou por um processo de remoção da maior parte da gordura. É uma fonte rica de cálcio e proteínas, com menor teor de gordura em comparação com o leite integral.',
        34.0,
        3.4,
        90.4,
        'Cálcio, Fósforo',
        'D,B12',
        0.2,
        'MILILITERS',
        'https://unlockwaystaticfiles.blob.core.windows.net/ingredients/skimmed_milk.png'
    );
INSERT INTO tb_ingredients (
        name,
        description,
        calories,
        proteins,
        water,
        minerals,
        vitamins,
        fats,
        measure,
        photo
    )
VALUES (
        'Açúcar',
        'O açúcar é um adoçante amplamente utilizado na culinária e na indústria alimentícia. É derivado de fontes como a cana-de-açúcar ou a beterraba e é usado para adoçar uma variedade de alimentos e bebidas.',
        387.0,
        0.0,
        0.0,
        'Nenhum significativo',
        '',
        0.0,
        'GRAMS',
        'https://unlockwaystaticfiles.blob.core.windows.net/ingredients/sugar.png'
    );
INSERT INTO tb_ingredients (
        name,
        description,
        calories,
        proteins,
        water,
        minerals,
        vitamins,
        fats,
        measure,
        photo
    )
VALUES (
        'Farinha de Trigo',
        'A farinha de trigo é um produto obtido da moagem dos grãos de trigo. É um ingrediente fundamental na produção de uma variedade de produtos assados, massas e outros alimentos.',
        364.0,
        10.3,
        13.0,
        'Ferro, Magnésio, Fósforo, Potássio',
        'B1,B3',
        1.2,
        'GRAMS',
        'https://unlockwaystaticfiles.blob.core.windows.net/ingredients/wheat_flour.png'
    );
INSERT INTO tb_ingredients (
        name,
        description,
        calories,
        proteins,
        water,
        minerals,
        vitamins,
        fats,
        measure,
        photo
    )
VALUES (
        'Alface',
        'A alface é uma folha verde amplamente consumida como parte de saladas e sanduíches. Ela é conhecida por sua textura crocante e sabor suave.',
        5.0,
        0.5,
        95.0,
        'Cálcio, Fósforo, Potássio',
        'A,K',
        0.2,
        'AMOUNT',
        'https://unlockwaystaticfiles.blob.core.windows.net/ingredients/lettuce.png'
    );
INSERT INTO tb_ingredients (
        name,
        description,
        calories,
        proteins,
        water,
        minerals,
        vitamins,
        fats,
        measure,
        photo
    )
VALUES (
        'Azeite de Oliva',
        'O azeite de oliva é um óleo extraído da azeitona, sendo uma fonte importante de gorduras saudáveis e utilizado na culinária e na preparação de alimentos.',
        884.0,
        0.0,
        0.0,
        'Cálcio, Ferro, Magnésio',
        'E,K',
        100.0,
        'MILILITERS',
        'https://unlockwaystaticfiles.blob.core.windows.net/ingredients/olive_oil.png'
    );
INSERT INTO tb_ingredients (
        name,
        description,
        calories,
        proteins,
        water,
        minerals,
        vitamins,
        fats,
        measure,
        photo
    )
VALUES (
        'Sal',
        'O sal é um mineral composto principalmente por cloreto de sódio e é amplamente utilizado como condimento na culinária para realçar o sabor dos alimentos.',
        0.0,
        0.0,
        0.0,
        'Sódio, Cloreto',
        '',
        0.0,
        'GRAMS',
        'https://unlockwaystaticfiles.blob.core.windows.net/ingredients/salt.png'
    );
INSERT INTO tb_ingredients (
        name,
        description,
        calories,
        proteins,
        water,
        minerals,
        vitamins,
        fats,
        measure,
        photo
    )
VALUES (
        'Pimenta',
        'A pimenta é um condimento picante obtido a partir de diversas espécies de plantas do gênero Capsicum. Ela é usada para adicionar sabor e calor aos alimentos.',
        40.0,
        2.0,
        88.0,
        'Cálcio, Fósforo, Potássio',
        'C,A',
        1.3,
        'AMOUNT',
        'https://unlockwaystaticfiles.blob.core.windows.net/ingredients/pepper.png'
    );
INSERT INTO tb_ingredients (
        name,
        description,
        calories,
        proteins,
        water,
        minerals,
        vitamins,
        fats,
        measure,
        photo
    )
VALUES (
        'Orégano',
        'O orégano é uma erva aromática utilizada na culinária para dar sabor a uma variedade de pratos, especialmente em pratos da cozinha italiana.',
        265.0,
        9.0,
        9.0,
        'Cálcio, Ferro, Magnésio',
        'K',
        4.3,
        'GRAMS',
        'https://unlockwaystaticfiles.blob.core.windows.net/ingredients/oregano.png'
    );
INSERT INTO tb_ingredients (
        name,
        description,
        calories,
        proteins,
        water,
        minerals,
        vitamins,
        fats,
        measure,
        photo
    )
VALUES (
        'Água',
        'A água é uma substância incolor, inodora e insípida essencial para a vida. Ela desempenha um papel vital em várias funções do corpo humano, incluindo hidratação e transporte de nutrientes.',
        0.0,
        0.0,
        100.0,
        'Não aplicável',
        '',
        0.0,
        'MILILITERS',
        'https://unlockwaystaticfiles.blob.core.windows.net/ingredients/water.png'
    );
INSERT INTO tb_ingredients (
        name,
        description,
        calories,
        proteins,
        water,
        minerals,
        vitamins,
        fats,
        measure,
        photo
    )
VALUES (
        'Mel',
        'O mel é um adoçante natural produzido pelas abelhas a partir do néctar das flores. É conhecido por seu sabor doce e suas propriedades medicinais.',
        304.0,
        0.4,
        17.0,
        'Cálcio, Ferro, Magnésio',
        'C,B6',
        0.0,
        'MILILITERS',
        'https://unlockwaystaticfiles.blob.core.windows.net/ingredients/honey.png'
    );
INSERT INTO tb_ingredients (
        name,
        description,
        calories,
        proteins,
        water,
        minerals,
        vitamins,
        fats,
        measure,
        photo
    )
VALUES (
        'Café',
        'O café é uma bebida popular preparada a partir dos grãos torrados da planta de café. É conhecido por seu sabor característico e efeitos estimulantes devido à cafeína.',
        2.0,
        0.2,
        98.0,
        'Potássio',
        '',
        0.0,
        'GRAMS',
        'https://unlockwaystaticfiles.blob.core.windows.net/ingredients/coffee.png'
    );
INSERT INTO tb_ingredients (
        name,
        description,
        calories,
        proteins,
        water,
        minerals,
        vitamins,
        fats,
        measure,
        photo
    )
VALUES (
        'Tofu',
        'O tofu, também conhecido como queijo de soja, é um alimento feito a partir da coalhada da soja. É uma fonte popular de proteína na dieta vegetariana e vegana.',
        144.0,
        15.8,
        79.0,
        'Cálcio, Ferro, Magnésio',
        'B1,B2',
        8.8,
        'GRAMS',
        'https://unlockwaystaticfiles.blob.core.windows.net/ingredients/tofu.png'
    );
INSERT INTO tb_ingredients (
        name,
        description,
        calories,
        proteins,
        water,
        minerals,
        vitamins,
        fats,
        measure,
        photo
    )
VALUES (
        'Cevada',
        'A cevada é um grão integral amplamente utilizado na produção de alimentos, como farinha de cevada, malte e como ingrediente em sopas e ensopados.',
        354.0,
        12.5,
        9.3,
        'Cálcio, Ferro, Magnésio',
        'B6,C',
        2.3,
        'GRAMS',
        'https://unlockwaystaticfiles.blob.core.windows.net/ingredients/barley.png'
    );
INSERT INTO tb_ingredients (
        name,
        description,
        calories,
        proteins,
        water,
        minerals,
        vitamins,
        fats,
        measure,
        photo
    )
VALUES (
        'Quinoa',
        'A quinoa é um pseudocereal cultivado pelos povos indígenas da região dos Andes. Ela é apreciada por sua alta qualidade nutricional e é uma excelente fonte de proteína vegetal.',
        120.0,
        4.0,
        72.0,
        'Ferro, Magnésio, Fósforo',
        'B6,E',
        1.9,
        'GRAMS',
        'https://unlockwaystaticfiles.blob.core.windows.net/ingredients/quinoa.png'
    );
INSERT INTO tb_ingredients (
        name,
        description,
        calories,
        proteins,
        water,
        minerals,
        vitamins,
        fats,
        measure,
        photo
    )
VALUES (
        'Sementes de Girassol',
        'As sementes de girassol são as sementes comestíveis do girassol. Elas são uma excelente fonte de nutrientes, incluindo gorduras saudáveis, proteínas e vitaminas.',
        584.0,
        20.8,
        5.8,
        'Ferro, Magnésio, Fósforo',
        'E,B1',
        51.5,
        'GRAMS',
        'https://unlockwaystaticfiles.blob.core.windows.net/ingredients/sunflower_seeds.png'
    );
INSERT INTO tb_ingredients (
        name,
        description,
        calories,
        proteins,
        water,
        minerals,
        vitamins,
        fats,
        measure,
        photo
    )
VALUES (
        'Amêndoas',
        'As amêndoas são sementes comestíveis extraídas do fruto da amendoeira. Elas são conhecidas por serem uma fonte de nutrientes, incluindo gorduras saudáveis, proteínas e vitaminas.',
        576.0,
        21.2,
        4.7,
        'Cálcio, Ferro, Magnésio',
        'E,B2',
        49.4,
        'GRAMS',
        'https://unlockwaystaticfiles.blob.core.windows.net/ingredients/almonds.png'
    );
INSERT INTO tb_ingredients (
        name,
        description,
        calories,
        proteins,
        water,
        minerals,
        vitamins,
        fats,
        measure,
        photo
    )
VALUES (
        'Manjericão',
        'O manjericão é uma erva aromática comumente usada na culinária de diversas cozinhas ao redor do mundo. Ele é conhecido por seu aroma distinto e sabor fresco.',
        23.0,
        3.2,
        92.1,
        'Cálcio, Ferro, Magnésio',
        'A,K',
        0.6,
        'AMOUNT',
        'https://unlockwaystaticfiles.blob.core.windows.net/ingredients/basil.png'
    );
-- Novos ingredientes
INSERT INTO tb_ingredients (
        name,
        description,
        calories,
        proteins,
        water,
        minerals,
        vitamins,
        fats,
        measure,
        photo
    )
VALUES (
        'Pão',
        'O pão é um alimento básico, feito de farinha, água e fermento. É amplamente consumido em diferentes culturas e formas.',
        265.0,
        9.0,
        38.0,
        'Fósforo, Sódio',
        'B1, B3',
        3.2,
        'GRAMS',
        'https://unlockwaystaticfiles.blob.core.windows.net/ingredients/pao.png'
    );
INSERT INTO tb_ingredients (
        name,
        description,
        calories,
        proteins,
        water,
        minerals,
        vitamins,
        fats,
        measure,
        photo
    )
VALUES (
        'Abobrinha',
        'A abobrinha é um vegetal leve, com baixo teor calórico, muito utilizado em saladas, refogados e grelhados.',
        17.0,
        1.2,
        95.0,
        'Potássio, Magnésio',
        'A, C',
        0.1,
        'GRAMS',
        'https://unlockwaystaticfiles.blob.core.windows.net/ingredients/abobrinha.png'
    );
INSERT INTO tb_ingredients (
        name,
        description,
        calories,
        proteins,
        water,
        minerals,
        vitamins,
        fats,
        measure,
        photo
    )
VALUES (
        'Couve',
        'A couve é uma hortaliça rica em fibras e nutrientes, muito utilizada em pratos brasileiros como o acompanhamento da feijoada.',
        43.0,
        3.6,
        89.0,
        'Cálcio, Ferro',
        'A, K',
        0.4,
        'GRAMS',
        'https://unlockwaystaticfiles.blob.core.windows.net/ingredients/couve.png'
    );
INSERT INTO tb_ingredients (
        name,
        description,
        calories,
        proteins,
        water,
        minerals,
        vitamins,
        fats,
        measure,
        photo
    )
VALUES (
        'Pimentão Vermelho',
        'O pimentão vermelho é uma variedade doce e rica em antioxidantes, excelente para saladas, refogados ou grelhados.',
        31.0,
        1.0,
        92.0,
        'Potássio, Magnésio',
        'A, C',
        0.3,
        'GRAMS',
        'https://unlockwaystaticfiles.blob.core.windows.net/ingredients/pimentao-vermelho.png'
    );
INSERT INTO tb_ingredients (
        name,
        description,
        calories,
        proteins,
        water,
        minerals,
        vitamins,
        fats,
        measure,
        photo
    )
VALUES (
        'Pepino',
        'O pepino é um vegetal fresco e hidratante, com alta quantidade de água e baixo valor calórico.',
        15.0,
        0.7,
        96.0,
        'Potássio, Magnésio',
        'K, C',
        0.1,
        'GRAMS',
        'https://unlockwaystaticfiles.blob.core.windows.net/ingredients/pepino.png'
    );
INSERT INTO tb_ingredients (
        name,
        description,
        calories,
        proteins,
        water,
        minerals,
        vitamins,
        fats,
        measure,
        photo
    )
VALUES (
        'Batata Doce',
        'A batata doce é um tubérculo nutritivo, rico em fibras e carboidratos de baixo índice glicêmico.',
        86.0,
        1.6,
        77.0,
        'Ferro, Potássio',
        'A,C,B6',
        0.1,
        'AMOUNT',
        'https://unlockwaystaticfiles.blob.core.windows.net/ingredients/batata-roxa.png'
    );
INSERT INTO tb_ingredients (
        name,
        description,
        calories,
        proteins,
        water,
        minerals,
        vitamins,
        fats,
        measure,
        photo
    )
VALUES (
        'Caju',
        'O caju é uma fruta tropical, rica em vitamina C, com sabor agridoce e muito usada em sucos e doces.',
        43.0,
        1.5,
        87.0,
        'Ferro, Cálcio',
        'C, B1',
        0.3,
        'GRAMS',
        'https://unlockwaystaticfiles.blob.core.windows.net/ingredients/caju.png'
    );
INSERT INTO tb_ingredients (
        name,
        description,
        calories,
        proteins,
        water,
        minerals,
        vitamins,
        fats,
        measure,
        photo
    )
VALUES (
        'Beterraba',
        'A beterraba é um vegetal de raiz com sabor adocicado, rico em antioxidantes e minerais.',
        43.0,
        1.6,
        88.0,
        'Ferro, Potássio',
        'A, C',
        0.2,
        'GRAMS',
        'https://unlockwaystaticfiles.blob.core.windows.net/ingredients/beterraba.png'
    );
INSERT INTO tb_ingredients (
        name,
        description,
        calories,
        proteins,
        water,
        minerals,
        vitamins,
        fats,
        measure,
        photo
    )
VALUES (
        'Coco',
        'O coco é uma fruta tropical rica em gorduras saudáveis, amplamente utilizado em sobremesas e pratos típicos.',
        354.0,
        3.3,
        47.0,
        'Magnésio, Fósforo',
        'E, B5',
        33.5,
        'GRAMS',
        'https://unlockwaystaticfiles.blob.core.windows.net/ingredients/coco.png'
    );
INSERT INTO tb_ingredients (
        name,
        description,
        calories,
        proteins,
        water,
        minerals,
        vitamins,
        fats,
        measure,
        photo
    )
VALUES (
        'Goiaba',
        'A goiaba é uma fruta tropical rica em fibras, vitamina C e antioxidantes, com sabor doce e aromático.',
        68.0,
        2.6,
        86.0,
        'Cálcio, Magnésio',
        'A, C',
        0.9,
        'GRAMS',
        'https://unlockwaystaticfiles.blob.core.windows.net/ingredients/goiaba.png'
    );
INSERT INTO tb_ingredients (
        name,
        description,
        calories,
        proteins,
        water,
        minerals,
        vitamins,
        fats,
        measure,
        photo
    )
VALUES (
        'Ervilha',
        'A ervilha é uma leguminosa rica em proteínas, fibras e minerais, amplamente utilizada em saladas e refogados.',
        81.0,
        5.4,
        79.0,
        'Ferro, Potássio',
        'A, K',
        0.4,
        'GRAMS',
        'https://unlockwaystaticfiles.blob.core.windows.net/ingredients/ervilha.png'
    );