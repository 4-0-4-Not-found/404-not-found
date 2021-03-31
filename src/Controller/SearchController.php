<?php

namespace App\Controller;


use App\Form\SearchartType;
use App\Repository\ArticleRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\HttpFoundation\Request;

/**
 * @Route("/")
 */
class SearchController extends AbstractController
{
    /**
     * @Route("/search", name="search_article")
     */
    public function searcharticale (Request $request,ArticleRepository $articleRepository)
    {
        $SearchartType = $this->createForm(SearchartType::class);

        if($SearchartType->handleRequest($request)->isSubmitted() && $SearchartType->isValid()) {

            $critere =$SearchartType->getData();
            dd($critere);
            $arts = $articleRepository->searcharticale($critere);

        }
        $SearchartType =$this ->createForm(SearchartType::class);
        return $this->render('search/index.html.twig', [
            'search_form' => $SearchartType->createView(),
        ]);

    }
}
