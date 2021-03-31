<?php


namespace App\Data;


use App\Entity\Category;
use App\Entity\Hotel;

class SearchData
{
    /**
     * @var int
     */
    public $page=1;
    /**
     * @var string
     */
   public $q='';
    /**
     * @var Category[]
     */
   public $categories=[];
    /**
     * @var string
     */
   public $location;
    /**
     * @var null|integer
     */
   public $max ;
   public $min;
    /**
     * @var bool
     */
   public $promotion=false;
   /**
    * @var bool
    */
    public $prix;

}