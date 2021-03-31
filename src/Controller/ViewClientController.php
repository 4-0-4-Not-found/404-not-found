<?php

namespace App\Controller;

use App\Entity\User;
use App\Form\ResetPassType;
use App\Form\UserFormType1;
use App\Form\UserType;
use App\Repository\UserRepository;
use phpDocumentor\Reflection\Types\Null_;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use Symfony\Component\HttpFoundation\Request;
use phpDocumentor\Reflection\DocBlock\Tags\Uses;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Security\Core\Encoder\UserPasswordEncoderInterface;
use Symfony\Component\Mailer\MailerInterface;
use Symfony\Component\Mime\Email;
use App\Service\generateToken;

class ViewClientController extends AbstractController
{

    /**
     * @Route("/", name="home")
     */
    public function home(): Response
    {
        return $this->render('index.html.twig', [
            'controller_name' => 'UserController',
        ]);
    }
    /**
     * @Route("/index", name="user_index", methods={"GET"})
     */
    public function index(UserRepository $userRepository): Response
    {
        return $this->render('user/index.html.twig', [
            'users' => $userRepository->findAll(),
        ]);
    }
    /**
     * @Route("/inscription", name="inscription")
     */
    public function inscription(Request $request,UserPasswordEncoderInterface $encoder ,MailerInterface $mailer): Response
    {
        $User=new User();
        $form=$this->createForm(UserType::class,$User);
        $token=(new generateToken())->token();
        $form->handleRequest($request);
        if($form->isSubmitted() && $form->isValid()){
            $User->setRoles(['ROLE_USER']);
            $User->setActivationToken($token);
            $User->setValidation(false);
            $User->setPassword($encoder->encodePassword($User,$User->getPassword()));
            $file=$form->get('image')->getData();

            $filename=md5(uniqid()).'.'.$file->guessExtension();
            try {
                $file->move(
                    $this->getParameter('images_directory'),
                    $filename
                );
            } catch (FileException $e) {
                // ... handle exception if something happens during file upload

            }
            $User->setImage($filename);
            $em=$this->getDoctrine()->getManager();
            $em->persist($User);
            $em->flush();

            $email = (new Email())
                ->from('hello@example.com')
                ->to($form['email']->getData())
                //->cc('cc@example.com')
                //->bcc('bcc@example.com')
                //->replyTo('fabien@example.com')
                //->priority(Email::PRIORITY_HIGH)
                ->subject('Time for Symfony Mailer!')
                ->text('Sending emails is fun again!')
                ->html("<p>il faut confirmer votre compte <a href=http://127.0.0.1:8000/activations/$token>verfier</a></p>");

            $mailer->send($email);

            return $this->redirectToRoute('app_login');
        }
        return $this->render('user/inscription.html.twig',[
            'form' => $form->createView(),
        ]);

    }



    /**
     * @Route("/activities", name="activities")
     */
    public function activities(): Response
    {
        return $this->render('activities.html.twig', [
            'controller_name' => 'UserController',
        ]);
    }
    /**
     * @Route("/admindash", name="admindash")
     */
    public function admindash(): Response
    {
        return $this->render('admindash.html.twig', [
            'controller_name' => 'UserController',
        ]);
    }
}
