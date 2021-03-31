<?php

namespace App\DataFixtures;

use App\Entity\Hotel;
use Doctrine\Bundle\FixturesBundle\Fixture;
use Doctrine\Persistence\ObjectManager;

class HotelFixture extends Fixture
{
    public function load(ObjectManager $manager)
    {
        for ($i=0; $i<100; $i++){
           $hotel=new Hotel();
        }
        // $product = new Product();
        // $manager->persist($product);

        $manager->flush();
    }
}
