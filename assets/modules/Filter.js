/**
 * @property {HTMLElement} pagination
 * @property {HTMLElement} content
 * @property {HTMLFormElement} form
 */
export default class Filter {
    /**
     *
     * @param {HTMLElement|null} element
     */
    constructor(element) {
        if(element===null){
            return
        }
        this.pagination=element.querySelector('.js-filter-pagination')
        this.content=element.querySelector('.js-filter-content')
        this.form=element.querySelector('.js-filter-form')
        this.bindEvent()


        console.log('je me construit')
    }

    /**
     * Ajoute les comportements au different element
     */
    bindEvent() {
        this.form.querySelectorAll('input[type=EntityType]').forEach(input =>{
            input.addEventListener('change', this.loadForm.bind(this))
        })
        this.loadUrl(e.target.getAttribute('href'))
    }
    async loadForm(){
        const data = new FormData(this.form)
        const url = new URL(this.form.getAttribute('action') || window.location.href)
        const params = new URLSearchParams()
        data.forEach((value,key) => {
            params.append(key,value)
        })
        return this.loadUrl(url.pathname + '?' +params.toString())
    }
        async loadUrl(url){
        const response = await fetch(url ,{
            headers: {
                'X-Requested-With':'XmlHttpRequest'
            }
        })
            if (response.status >=200 && response.status < 300) {
               const data=await response.json()
                this.content.innerHTML=data.content
                history.replaceState({},'',url)
            } else {
                console.error(response)
            }
        }

}