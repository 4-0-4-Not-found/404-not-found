<?php

namespace App\Controller;

use App\Entity\Category;
use App\Entity\Hotel;
use App\Form\HotelType;
use App\Repository\HotelRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\File\Exception\FileException;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Serializer\Exception\ExceptionInterface;
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;

/**
 * @Route("/hotel")
 */
class HotelController extends AbstractController
{
    /**
     * @Route("/", name="hotel_index", methods={"GET"})
     * @param HotelRepository $hotelRepository
     * @return Response
     */
    public function index(HotelRepository $hotelRepository): Response
    {
        return $this->render('hotel/index.html.twig', [
            'hotels' => $hotelRepository->findAll(),
        ]);
    }

    /**
     * @Route("/new", name="hotel_new", methods={"GET","POST"})
     * @param Request $request
     * @return Response
     */
    public function new(Request $request): Response
    {
        $hotel = new Hotel();
        $form = $this->createForm(HotelType::class, $hotel);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $file = $form->get('image')->getData();
            $filename = md5(uniqid()) . '.' . $file->guessExtension();
            $entityManager = $this->getDoctrine()->getManager();
            try {
                $file->move(
                    $this->getParameter('images_directory'),
                    $filename

                );
            } catch (FileException $e) {
                // ... handle exception if something happens during file upload

            }
            $hotel->setImage($filename);
            $entityManager->persist($hotel);
            $entityManager->flush();

            return $this->redirectToRoute('hotel_index');

        }

        return $this->render('hotel/new.html.twig', [
            'hotel' => $hotel,
            'form' => $form->createView(),
        ]);
    }

    /**
     * @Route("/{id}", name="hotel_show", methods={"GET"})
     * @param Hotel $hotel
     * @return Response
     */
    public function show(Hotel $hotel): Response
    {
        return $this->render('hotel/show.html.twig', [
            'hotel' => $hotel,
        ]);
    }

    /**
     * @Route("/{id}/edit", name="hotel_edit", methods={"GET","POST"})
     * @param Request $request
     * @param Hotel $hotel
     * @return Response
     */
    public function edit(Request $request, Hotel $hotel): Response
    {
        $form = $this->createForm(HotelType::class, $hotel);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $this->getDoctrine()->getManager()->flush();

            return $this->redirectToRoute('hotel_index');
        }

        return $this->render('hotel/edit.html.twig', [
            'hotel' => $hotel,
            'form' => $form->createView(),
        ]);
    }

    /**
     * @Route("/{id}", name="hotel_delete", methods={"DELETE"})
     * @param Request $request
     * @param Hotel $hotel
     * @return Response
     */
    public function delete(Request $request, Hotel $hotel): Response
    {
        if ($this->isCsrfTokenValid('delete' . $hotel->getId(), $request->request->get('_token'))) {
            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->remove($hotel);
            $entityManager->flush();
        }

        return $this->redirectToRoute('hotel_index');
    }


// public function searchNom (HotelRepository $repository, Request $request) {
//     $nom=$request->get('search')
//        $nom=$request->get('search');
//        $hotel=$repository->findOneBySomeField($nom);
//        return $this->render('management/search.html.twig', ['hotel'=>$hotel]);
// }

    /**
     * @param HotelRepository $repository
     * @param Request $request
     * @return Response
     * @Route("/searchNom" , name="search")
     */
    public function searchNom(HotelRepository $repository, Request $request)
    {
        $em = $this->getDoctrine()->getManager();
        $hotel = $request->get('search');
        $repository = $em->getRepository(Hotel::class);
        $query = $repository->createQueryBuilder('h')
            ->andWhere('h.nom LIKE :nom')
            ->setParameter('nom', '%' . $hotel . '%')
            ->orderBy('h.nom', 'ASC')
            ->getQuery();
        $hotel = $query->getResult();
        return $this->render('management/hotel.html.twig', ['hotels' => $hotel]);
   }
    /**
     * @param HotelRepository $repository
     * @param Request $request
     * @return Response
     * @Route("/searchAct" , name="search")
     */
    public function searchAct(HotelRepository $repository, Request $request)
    {
        $em = $this->getDoctrine()->getManager();
        $hotel = $request->get('search');
        $repository = $em->getRepository(Hotel::class);
        $query = $repository->createQueryBuilder('h')
            ->andWhere('h.activities LIKE :activities')
            ->setParameter('activities', '%'. $hotel . '%')
            ->orderBy('h.activities', 'ASC')
            ->getQuery();
        $hotel = $query->getResult();
        return $this->render('management/hotel.html.twig', ['hotels' => $hotel]);
    }
    /**
     * @param HotelRepository $repository
     * @param Request $request
     * @return Response
    * @Route("/searchLieu" , name="search")
   */
    public function searchLieu(HotelRepository $repository, Request $request)
    {
        $em = $this->getDoctrine()->getManager();
        $hotel = $request->get('search');
        $repository = $em->getRepository(Hotel::class);
        $query = $repository->createQueryBuilder('h')
            ->andWhere('h.location LIKE :location')
            ->setParameter('location', '%'. $hotel . '%')
            ->orderBy('h.location', 'ASC')
            ->getQuery();
        $hotel = $query->getResult();
        return $this->render('management/hotel.html.twig', ['hotels' => $hotel]);
    }
}
