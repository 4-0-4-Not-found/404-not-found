<?php

namespace App\Entity;

use App\Repository\CategoryRepository;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Doctrine\ORM\Mapping as ORM;

/**
 * @ORM\Entity(repositoryClass=CategoryRepository::class)
 */
class Category
{
    /**
     * @ORM\Id
     * @ORM\GeneratedValue
     * @ORM\Column(type="integer")
     */
    private $id;

    /**
     * @ORM\Column(type="string", length=255)
     */
    private $name;

//    /**
//     * @ORM\ManyToMany(targetEntity=Article::class, mappedBy="categories")
//     */
//    private $articles;

    /**
     * @ORM\ManyToMany(targetEntity=Produit::class, mappedBy="categories")
     */
    private $produits;

    public function __construct()
    {
//        $this->articles = new ArrayCollection();
        $this->produits = new ArrayCollection();
    }

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getName(): ?string
    {
        return $this->name;
    }

    public function setName(string $name): self
    {
        $this->name = $name;

        return $this;
    }

//    /**
//     * @return Collection|Article[]
//     */
//    public function getArticles(): Collection
//    {
//        return $this->articles;
//    }
//
//    public function addArticle(Article $article): self
//    {
//        if (!$this->articles->contains($article)) {
//            $this->articles[] = $article;
//            $article->addCategory($this);
//        }
//
//        return $this;
//    }
//
//    public function removeArticle(Article $article): self
//    {
//        if ($this->articles->removeElement($article)) {
//            $article->removeCategory($this);
//        }
//
//        return $this;
//    }

    /**
     * @return Collection|Produit[]
     */
    public function getProduits(): Collection
    {
        return $this->produits;
    }

    public function addProduit(Produit $produit): self
    {
        if (!$this->produits->contains($produit)) {
            $this->produits[] = $produit;
            $produit->addCategory($this);
        }

        return $this;
    }

    public function removeProduit(Produit $produit): self
    {
        if ($this->produits->removeElement($produit)) {
            $produit->removeCategory($this);
        }

        return $this;
    }
    public function __toString()
    {
        return $this->name;
    }
}
