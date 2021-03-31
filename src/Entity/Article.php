<?php

namespace App\Entity;

use App\Repository\ArticleRepository;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Doctrine\ORM\Mapping as ORM;
use phpDocumentor\Reflection\Types\Integer;

/**
 * @ORM\Entity(repositoryClass=ArticleRepository::class)
 */
class Article
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
    private $nom;

    /**
     * @ORM\Column(type="text")
     */
    private $description;

    /**
     * @ORM\Column(type="string", length=255)
     */
    private $image;

    /**
     * @ORM\Column(type="integer")
     */
    private $prix;

//    /**
//     * @ORM\ManyToMany(targetEntity=category::class, inversedBy="articles")
//     */
//    private $categories;

    /**
     * @ORM\Column(type="boolean")
     */
    private $promo;

//    public function __construct()
//    {
//        $this->categories = new ArrayCollection();
//    }

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getNom(): ?string
    {
        return $this->nom;
    }

    public function setNom(string $nom): self
    {
        $this->nom = $nom;

        return $this;
    }

    public function getDescription(): ?string
    {
        return $this->description;
    }

    public function setDescription(string $description): self
    {
        $this->description = $description;

        return $this;
    }

    public function getImage(): ?string
    {
        return $this->image;
    }

    public function setImage(string $image): self
    {
        $this->image = $image;

        return $this;
    }

    public function getPrix(): ?Integer
    {
        return $this->prix;
    }

    public function setPrix(Integer $prix): self
    {
        $this->prix = $prix;

        return $this;
    }

//    /**
//     * @return Collection|category[]
//     */
//    public function getCategories(): Collection
//    {
//        return $this->categories;
//    }
//
//    public function addCategory(category $category): self
//    {
//        if (!$this->categories->contains($category)) {
//            $this->categories[] = $category;
//        }
//
//        return $this;
//    }
//
//    public function removeCategory(category $category): self
//    {
//        $this->categories->removeElement($category);
//
//        return $this;
//    }

    public function getPromo(): ?bool
    {
        return $this->promo;
    }

    public function setPromo(bool $promo): self
    {
        $this->promo = $promo;

        return $this;
    }
}
