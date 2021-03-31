<?php

namespace App\Controller;

use App\Data\SearchData;
use App\Entity\Hotel;
use App\Entity\Reservation;
use App\Entity\ReservHotel;
use App\Entity\ResHotel;
use App\Form\ReservationType;
use App\Form\ResHotelType;
use App\Form\SearchForm;
use App\Notification\reservationNotification;
use App\Repository\HotelRepository;
use App\Repository\ReservationRepository;
use Doctrine\ORM\EntityManagerInterface;
use http\Env\Request;
use Knp\Component\Pager\Pagination\PaginationInterface;
use Knp\Component\Pager\PaginatorInterface;
use Swift_Mailer;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Mailer\MailerInterface;
use Symfony\Component\Mime\Email;
use Symfony\Component\Routing\Annotation\Route;

/**
 * @Route("/management")
 */
class ManagementController extends AbstractController
{
    private $repository;

    /**
     * @Route("/managementH", name="managementH", methods={"GET"})
     * @param HotelRepository $hotelRepository
     * @param PaginatorInterface $pager
     * @param SearchData $search
     * @param \Symfony\Component\HttpFoundation\Request $request
     * @return Response
     */
    public function index(HotelRepository $hotelRepository ,PaginatorInterface $pager,SearchData $search,  \Symfony\Component\HttpFoundation\Request $request): Response
    {

        $data= new SearchData();
        $data->page=$request->get('page',1);
        $form=$this->createForm(SearchForm::class,$data);
        $form->handleRequest($request);
        $hotels=$hotelRepository->findSearch($data);

//        if($request->isXmlHttpRequest()){
//            return new JsonResponse([
//                 'content'=>$this->renderView('hotel/_hotel.html.twig',['hotels'=>$hotels])
//            ]);
//        }
        $search->prix;
        if($prix=true){
            $hotel=$hotelRepository->findTri($data);
        }


        return $this->render('management/hotel.html.twig', [
            'hotels' => $hotels,
              'hotel'=>$hotel,
//            'hotel'=>$hotel,
            'form'=>$form->createView()
        ]);
    }

    /**
     * @Route ("/{nom}",name="detail")
     * @param Hotel $hotel
     * @param \Symfony\Component\HttpFoundation\Request $request
     * @param Swift_Mailer $mailer
     * @return Response
     */
    public function show(Hotel $hotel , \Symfony\Component\HttpFoundation\Request $request, Swift_Mailer $mailer): Response {

        $ReservHotel=new ReservHotel();
        $ReservHotel->setHotel($hotel);
        $form=$this->createForm(ResHotelType::class,$ReservHotel);
        $form->handleRequest($request);
        if($form->isSubmitted()&& $form->isValid()) {
            $message=( new \Swift_Message('Reservation de '.$ReservHotel->getHotel()->getNom()))
                ->setFrom('noreply@agence.tn')
                ->setTo('reservation@agence.tn')
                ->setReplyTo($ReservHotel->getEmail())
                ->setBody($this->renderView('emails/reservation.html.twig',[
                    'ReservHotel'=>$ReservHotel
                ]),'text/html');
            $mailer->send($message);

           $this->addFlash('success','Votre reservation a été bien enregistrer');
           return $this->redirectToRoute('detail',[
               'nom'=>$hotel->getNom()
           ]);
        }
        return $this->render('management/detail.html.twig', [
            'hotel' => $hotel,
            'form' => $form->createView()
        ]);



    }

//    /**
//     * @param Hotel $hotel
//     * @return Response
//     * @Route ("/streetmap",name="streetmap")
//     */
//    public function streetmap(Hotel $hotel){
//        $hotel=new Hotel();
//        return $this->render('streetmap.html.twig',[
//            $hotel=>$hotel
//        ]);

//
//    /**
//     * @Route("/managementR", name="managementR", methods={"GET","POST"})
//     * @param ReservationRepository $reservationRepository
//     * @return Response
//     */
//    public function res (ReservationRepository $reservationRepository): Response {
//
//        return $this->render('reservation/reservation.html.twig', [
//            'reservations' => $reservationRepository->findAll(),
//
//        ]);
//    }


}
