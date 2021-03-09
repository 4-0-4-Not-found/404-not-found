<?php


namespace App\Service;


class generateToken

{
    /**
     * @return string
     */
 public function token(string $char="ABCDEFGHIJKLMNOPQRSITUVWYXZ0123456789"):string
 {  $t="";
    for ($i=0;$i<20;$i++)
    {
          $t.=$char[rand(0,strlen($char)-1)];
    }
    return $t;
 }

}