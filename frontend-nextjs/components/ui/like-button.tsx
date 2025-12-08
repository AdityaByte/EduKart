import { Button } from '@/components/ui/button'
import { Heart } from 'lucide-react'

const LikeButton = () => {
    return (
        <Button
            size='icon'
            className='bg-red-600/10 text-red-600 hover:bg-red-600/20 focus-visible:ring-red-600/20 dark:bg-red-400/10 dark:text-red-400 dark:hover:bg-red-400/20 dark:focus-visible:ring-red-400/40'
        >
            <Heart />
            <span className='sr-only'>Cart</span>
        </Button>
    )
}

export default LikeButton