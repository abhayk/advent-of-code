package aoc2015;

import java.util.ArrayList;
import java.util.List;

public class Day21
{
    static class Item
    {
        String name;
        int cost;
        int damage;
        int armor;

        public Item(String name, int cost, int damage, int armor)
        {
            this.name = name;
            this.cost = cost;
            this.damage = damage;
            this.armor = armor;
        }
    }

    static class Player
    {
        int maxhp;
        int damage;
        int armor;
        int currenthp;
        int goldSpent;

        public Player( int maxhp)
        {
            this.maxhp = maxhp;
            this.currenthp = maxhp;
        }
        void hit( Player p2 )
        {
            p2.currenthp -= ( this.damage - p2.armor );
        }
        void reset()
        {
            resetHP();
            this.damage = 0;
            this.armor = 0;
            this.goldSpent = 0;
        }
        void resetHP()
        {
            this.currenthp = this.maxhp;
        }
        void addItem( Item item )
        {
            this.damage += item.damage;
            this.armor += item.armor;
            this.goldSpent += item.cost;
        }
    }

    private static boolean playerWinsFight( Player player, Player boss )
    {
        while( true )
        {
            player.hit( boss );
            if( boss.currenthp <= 0 )
                return true;
            boss.hit( player );
            if( player.currenthp <= 0 )
                return false;
        }
    }

    private static void simulateFights( List<Item> weapons, List<Item> armor, List<Item> rings, Player boss )
    {
        int minGoldSpentForAWin = Integer.MAX_VALUE;
        int maxGoldSpentForALoss = Integer.MIN_VALUE;
        Player player = new Player( 100 );
        for( Item weapon : weapons )
        {
            for( int i=-1; i<armor.size(); i++ )
            {
                for( int j = -1; j<rings.size(); j++ )
                {
                    for( int k = -1; k<rings.size(); k++ )
                    {
                        if(j == k && k >= 0 )
                            continue;

                        player.addItem( weapon );
                        if( i >= 0 )
                            player.addItem( armor.get( i ) );
                        if( j >= 0 )
                            player.addItem( rings.get( j ));
                        if( k >= 0 )
                            player.addItem( rings.get( k ));

                        if( playerWinsFight( player, boss))
                        {
                            if( player.goldSpent < minGoldSpentForAWin )
                                minGoldSpentForAWin = player.goldSpent;
                        }
                        else
                        {
                            if( player.goldSpent > maxGoldSpentForALoss )
                                maxGoldSpentForALoss = player.goldSpent;
                        }
                        player.reset();
                        boss.resetHP();
                    }
                }
            }
        }
        System.out.println("Min gold for a win - " + minGoldSpentForAWin );
        System.out.println("Max gold for a loss - " + maxGoldSpentForALoss );
    }

    public static void main(String[] args)
    {
        List<Item> weapons = new ArrayList<>();
        weapons.add( new Item("Dagger", 8, 4, 0));
        weapons.add( new Item("Shortsword", 10, 5, 0));
        weapons.add( new Item("Warhammer", 25, 6, 0));
        weapons.add( new Item("Longsword", 40, 7, 0));
        weapons.add( new Item("Greataxe", 74, 8, 0));

        List<Item> armor = new ArrayList<>();
        armor.add( new Item("Leather", 13, 0, 1));
        armor.add( new Item("Chainmail", 31, 0, 2));
        armor.add( new Item("Splintmail", 53, 0, 3));
        armor.add( new Item("Bandedmail", 75, 0, 4));
        armor.add( new Item("Platemail", 102, 0, 5));

        List<Item> rings = new ArrayList<>();
        rings.add( new Item("Damage+1", 25, 1, 0));
        rings.add( new Item("Damage+2", 50, 2, 0));
        rings.add( new Item("Damage+3", 100, 3, 0));
        rings.add( new Item("Damage+1", 20, 0, 1));
        rings.add( new Item("Damage+2", 40, 0, 2));
        rings.add( new Item("Damage+3", 80, 0, 3));

        Player boss = new Player(103);
        boss.damage = 9;
        boss.armor = 2;

        simulateFights( weapons, armor, rings, boss );
    }
}
