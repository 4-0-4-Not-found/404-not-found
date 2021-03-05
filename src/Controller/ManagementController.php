<?php

namespace App\Controller;

use App\Repository\ActiviteRepository;
use App\Repository\HotelRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

/**
 * @Route("/management")
 */
class ManagementController extends AbstractController
{
    /**
     * @Route("/activiteclient", name="activite_management", methods={"GET"})
     */
    public function index(ActiviteRepository $activiteRepository): Response
    {
        return $this->render('management/activities.html.twig', [
            'activites' => $activiteRepository->findAll(),

        ]);
    }
    /**
     * @Route("/clienthotel", name="hotel_index", methods={"GET"})
     */
    public function indexhotel(HotelRepository $hotelRepository): Response
    {
        return $this->render('management/hotel.html.twig', [
            'hotels' => $hotelRepository->findAll(),
        ]);
    }

}
