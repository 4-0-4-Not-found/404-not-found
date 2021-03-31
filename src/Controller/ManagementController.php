<?php

namespace App\Controller;

use App\Entity\Article;
use App\Repository\ArticleRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use App\Repository\ProduitRepository;


/**
 * @Route("/management")
 */
class ManagementController extends AbstractController
{
    /**
     * @Route("/art", name="management" )
     */
    public function rechercheByniveauAction(Request $request,ArticleRepository $articleRepository)
    {
        $em=$this->getDoctrine()->getManager();
        $article =$em->getRepository(Article::class)->findAll();
        if( $request->isMethod("Post"))
        {
            $nom = $request->get('nom');
            $article = $em->getRepository(Article::class)->findBy(array('nom'=>$nom),array('description'=>'ASC'));
        }
        return $this->render("management/article.html.twig",array('article'=>$article));
    }

}
