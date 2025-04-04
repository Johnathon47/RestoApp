SELECT dish."name", ingredient."name", required_quantity, dish_ingredient.unit FROM dish_ingredient
        INNER JOIN ingredient ON dish_ingredient.id_ingredient = ingredient."id"
        INNER JOIN dish ON dish_ingredient.id_dish = dish."id"
        ORDER BY ingredient."id";