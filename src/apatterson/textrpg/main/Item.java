package apatterson.textrpg.main;

public class Item {
    public String name;
    public String type; // "weapon", "armor", "accessory"
    public int atkBonus;
    public int defBonus;
    public int hpBonus;
    public int value; //gold cost

    public Item(String name, String type, int atkBonus, int defBonus, int hpBonus, int value) {
        this.name = name;
        this.type = type;
        this.atkBonus = atkBonus;
        this.defBonus = defBonus;
        this.hpBonus = hpBonus;
        this.value = value;
    }

    public static Item[] createCommonItems() {
        return new Item[] {
          //weapons
          new Item("Iron Sword", "weapon", 5, 0, 0, 10),
          new Item("Steel Sword", "weapon", 8, 0, 0, 20),
          new Item("Mythril Blade", "weapon", 12, 0, 5, 35),
          //armor
          new Item("Leather Armor", "armor", 0, 3, 5, 10),
          new Item("Chain Mail", "armor", 0, 6, 10, 20),
          new Item("Plate Armor", "armor", 0, 10, 15, 35),
          //accessories
          new Item("Health Ring", "accessory", 0, 0, 20, 15),
          new Item("Warrior's Band", "accessory", 3, 3, 10, 25),
          new Item("Divine Charm", "accessory", 5, 5, 25, 40)
        };
    }
}
