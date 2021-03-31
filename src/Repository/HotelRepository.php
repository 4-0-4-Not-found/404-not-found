<?php

namespace App\Repository;

use App\Controller\HotelController;
use App\Data\SearchData;
use App\Entity\Hotel;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\DBAL\Query\QueryBuilder;
use Doctrine\ORM\NonUniqueResultException;
use Doctrine\ORM\Query;
use Doctrine\Persistence\ManagerRegistry;
use Knp\Component\Pager\Pagination\PaginationInterface;
use Knp\Component\Pager\PaginatorInterface;

/**
 * @method Hotel|null find($id, $lockMode = null, $lockVersion = null)
 * @method Hotel|null findOneBy(array $criteria, array $orderBy = null)
 * @method Hotel[]    findAll()
 * @method Hotel[]    findBy(array $criteria, array $orderBy = null, $limit = null, $offset = null)
 */
class HotelRepository extends ServiceEntityRepository
{

    /**
     * @var PaginatorInterface
     */
    private $pager;

    public function __construct(ManagerRegistry $registry, PaginatorInterface $pager)
    {
        parent::__construct($registry, Hotel::class);
        $this->pager = $pager;
    }


//    public function searchNom($nom){
//        return $this->createQueryBuilder('s')
//            ->where('s.nom LIKE :nom')
//            ->setParameter('nom', '%'.$nom.'%' )
//            ->getQuery()->getResult();
//    }

    // /**
    //  * @return Hotel[] Returns an array of Hotel objects
    //  */
    /*
    public function findByExampleField($value)
    {
        return $this->createQueryBuilder('h')
            ->andWhere('h.exampleField = :val')
            ->setParameter('val', $value)
            ->orderBy('h.id', 'ASC')
            ->setMaxResults(10)
            ->getQuery()
            ->getResult()
        ;
    }
    */


//    public function findOneBySomeField($nom): ?Hotel
//    {
//
//            return $this->createQueryBuilder('h')
//                ->andWhere('h.nom = :nom')
//                ->setParameter('nom','%'.$nom.'%')
//                ->getQuery()
//                ->getResult();
//
//
//    }

    /**
     * @param SearchData $search
     * @return PaginationInterface
     */
    public function findSearch( SearchData $search): PaginationInterface
    {

            $query =$this
                ->createQueryBuilder('h')
            ->select('h');
                if (!empty($search->q)) {
                    $query = $query
                        ->andWhere('h.nom LIKE :q')
                        ->setParameter('q', "%{$search->q}%");
                }
        if (!empty($search->min)) {
            $query = $query
                ->andWhere('h.price >= :min')
                ->setParameter('min', $search->min);

        }
        if (!empty($search->max)) {
            $query = $query
                ->andWhere('h.price <= :max')
                ->setParameter('max', $search->max);

        }
        if (!empty($search->prix)) {
            if($prix=true) {
                $query = $query
                    ->andWhere('h.price >= 10')
                    ->orderBy('h.price', 'DESC');
            }else
                $query=$query
                    ->andWhere('h.price >=10')
                    ->orderBy('h.price','ASC');
        }
        if (!empty($search->promotion)) {
            $query = $query
                ->andWhere('h.promotion =1');
                }
        if (!empty($search->categories)) {
            $query = $query
                ->andWhere('h.id IN (:category)')
                ->setParameter('category',$search->categories);
        }

        if (!empty($search->location)) {
            $query = $query
                ->andWhere('h.location LIKE :location')
                ->setParameter('location', "%{$search->location}%");
        }
//          if(!empty($search->prix)){
//              $query=$query
//                  ->addOrderBy('h.price','ASC');
//
//          }

            $query=$query->getQuery();
           return $this->pager->paginate(
               $query,
               $search->page,
               3
           );

    }

    /**
     * @param SearchData $search
     * @return PaginationInterface
     */
    public function findTri(SearchData $search) :PaginationInterface
    {
        $em = $this->getEntityManager();
            $query = $em->createQuery('select s from App\Entity\Hotel s order by s.price ASC');
        return $this->pager->paginate(
            $query,
            $search->page,2
        );
    }



//        $query=$this
//            ->createQueryBuilder('p')
//             ->select('p');
//        if (!empty($search->q)){
//          $query=$query
//              ->andWhere('p.nom LIKE :q')
//              ->setParameter('q',"%{$search->q}%");
//        }
//        return $query->getQuery()->getResult();



}
