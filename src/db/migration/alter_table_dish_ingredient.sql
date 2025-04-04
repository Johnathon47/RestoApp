-- ALTER TABLE dish_ingredient just add ON DELETE CASCADE (automatiser la suppression juste simplement en faisant delete from dish where id = ? efface les liaisons avec dish_ingredient;)
ALTER TABLE dish_ingredient
ADD CONSTRAINT fk_dish_ingredient
FOREIGN KEY (id_dish) REFERENCES dish(id) ON DELETE CASCADE;