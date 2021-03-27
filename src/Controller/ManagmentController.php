<?php

namespace App\Controller;

use App\Form\SearchActiviteType;
use App\Repository\ProduitRepository;
use Knp\Component\Pager\PaginatorInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Knp\Component\Pager\Pagination\PaginationInterface;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\HttpFoundation\Request;
use App\Repository\ActiviteRepository;
use Symfony\Component\Form\Extension\Core\Type\SearchType;
use Symfony\Component\Mailer\MailerInterface;


/**
 * @Route("/managment")
 */
class ManagmentController extends AbstractController
{
    /**
     * @Route("/managmentt", name="managment" )
     */
    public function index(ActiviteRepository $activiteRepository,
                          Request $request,
                          PaginatorInterface $paginator): Response
    {
        $data=$activiteRepository->findAll();
        $form = $this->createForm(SearchActiviteType::class);
        $search = $form->handleRequest($request);
        // On recherche les annonces correspondant aux mots clés
        if($form->isSubmitted() && $form->isValid()){
            $data=$activiteRepository->search(
                $search->get('mots')->getData()
            );
        }
        $activite =$paginator->paginate(
            $data,
            $request->query->getInt('page',1),
            2
        );
        return $this->render('managment/activities.html.twig', [
            'activites' => $activite,
            'form' => $form->createView()

        ]);
    }
//Produit

    /**
     * @Route("/managmentP", name="managmentP", methods={"GET"})
     */
    public function inde(ProduitRepository $produitRepository): Response
    {
        return $this->render('managment/Produits.html.twig', [
            'produits' => $produitRepository->findAll(),
        ]);
    }
}
