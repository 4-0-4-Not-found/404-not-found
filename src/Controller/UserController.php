<?php

namespace App\Controller;

use App\Entity\Article;
use App\Entity\User;
use App\Form\UserType;
use App\Repository\UserRepository;
use App\Service\generateToken;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\IsGranted;
use Symfony\Component\Security\Core\Encoder\UserPasswordEncoderInterface;

/**
 * @Route("/user")
 */
class UserController extends AbstractController
{

    /**
     * @Route("/", name="user_index", methods={"GET"})
     */
    public function index(UserRepository $userRepository): Response
    {
        return $this->render('user/index.html.twig', [
            'users' => $userRepository->findAll(),
        ]);
    }
    /**
     * @Route("/search", name="user_search")
     */
    public function search(UserRepository $userRepository, Request $request): Response
    {
        $data=$request->get('search');
        $user=$userRepository->findBy(['nom'=>$data]);
        return $this->render('user/index.html.twig', [
            'users' => $user,
        ]);
    }
    /**
     * @Route("/new", name="user_new", methods={"GET","POST"})
     */
    public function new(Request $request,UserPasswordEncoderInterface $encoder): Response
    {
        $user = new User();
        $form = $this->createForm(UserType::class, $user);
        $form->handleRequest($request);
        if ($form->isSubmitted() && $form->isValid()) {
            $user->setRoles(['ROLE_USER']);
            $user->setActivationToken((new generateToken())->token())
            ->setValidation(true);
            $user->setRoles([$request->get('role')]);
            $user->setPassword($encoder->encodePassword($user,$user->getPassword()));
            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->persist($user);
            $entityManager->flush();

            return $this->redirectToRoute('user_index');
        }
        return $this->render('user/new.html.twig', [
            'user' => $user,
            'form' => $form->createView(),
        ]);
    }

    /**
     * @Route("/{id}", name="user_show", methods={"GET"})
     */
    public function show(User $user): Response
    {
        return $this->render('user/show.html.twig', [
            'user' => $user,
        ]);
    }
    /**
     * @Route("/Athlete/{id}", name="athtelearticles", methods={"GET"})
     */
    public function showathelete(int $id): Response
    {
        $Athtele=$this->getDoctrine()->getRepository(user::class)->find($id);
        $article=$Athtele->getArticles();


        return $this->render('management/athteleArticles.html.twig', [
            'article' => $article,
            'athlete' => $Athtele
        ]);
    }
    /**
     * @Route("/{id}/edit", name="user_edit", methods={"GET","POST"})
     */
    public function edit(Request $request, User $user,UserPasswordEncoderInterface $encoder): Response
    {
        if($user !== $this->getUser()) {
            $form = $this->createForm(UserType::class, $user);
            $form->handleRequest($request);

            if ($form->isSubmitted() && $form->isValid()) {
                $user->setRoles([$request->get('role')]);
                $user->setPassword($encoder->encodePassword($user, $user->getPassword()));
                $this->getDoctrine()->getManager()->flush();
                return $this->redirectToRoute('user_index');
            }

            return $this->render('user/edit.html.twig', [
                'user' => $user,
                'form' => $form->createView(),
            ]);
        }else{
            if (($content = $request->getContent()) && $request->getContentType() === 'json') {
                $userData = json_decode($content, true);
                $user->setNom($userData['lname'])
                     ->setPrenom($userData['fname'])
                     ->setTel($userData['tel']);
                $this->getDoctrine()->getManager()->flush();
            }else{
                return $this->json(['error'=>'bad request :p '],400);
            }
            return $this->json(["status"=>200],201);
        }
    }

    /**
     * @Route("/{id}", name="user_delete", methods={"DELETE"})
     */
    public function delete(Request $request, User $user): Response
    {
        if ($this->isCsrfTokenValid('delete'.$user->getId(), $request->request->get('_token'))) {
            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->remove($user);
            $entityManager->flush();
        }
        return $this->redirectToRoute('user_index');
    }
    /**
     * @Route("/activation/{id}", name="activation")
     * @var User $user
     * @IsGranted("ROLE_ADMIN")
     */
    public function  activation(User $user):Response
    {
        $user->setValidation(!$user->getValidation());
        $this->getDoctrine()->getManager()->flush();
        return $this->json([],200);
    }
}
