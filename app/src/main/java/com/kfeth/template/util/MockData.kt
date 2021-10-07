package com.kfeth.template.util

import com.kfeth.template.data.Article

fun mockArticles(): List<Article> {
    return listOf(
        Article(
            url = "https://www.nytimes.com/2021/10/06/us/debt-ceiling-filibuster.html",
            author = "Carl Hulse",
            title = "As Debt Ceiling Vote Looms, Democrats Reconsider Filibuster - The New York Times",
            description = "Top Democrats say the Republican refusal to allow a vote to avert a federal default is the most powerful argument yet for weakening the Senate’s signature procedural weapon.",
            imageUrl = "https://static01.nyt.com/images/2021/10/06/us/politics/06DC-FILIBUSTER-1/merlin_195855555_513907d1-4751-4460-a8b1-d838388b9ae9-facebookJumbo.jpg",
            publishedAt = "2021-10-06T13:47:19Z",
            content = "Democrats urging a change in filibuster rules said they hoped the nature of the debt limit fight, with widespread warnings of global economic calamity if the government defaults and Republicans refus… [+4388 chars]"
        ),
        Article(
            url = "https://www.theverge.com/good-deals/2021/10/6/22710829/apple-ipad-mini-airpods-max-dji-mavic-air-2-drone-amazon-fire-hd-10-bundle-deal-sale",
            author = "Sheena Vasani",
            title = "Apple’s new iPad Mini is already discounted at Adorama - The Verge",
            description = "The 2021 iPad Mini is currently on sale at Adorama for \$499, the first discount the miniature tablet has received. There are also discounts today on the second-gen Apple Pencil, DJI Mavic Air 2, and Amazon’s Fire HD 10.",
            imageUrl = "https://cdn.vox-cdn.com/thumbor/MykHmRnHL6O-9_uzk4V5NVQUf4E=/0x146:2040x1214/fit-in/1200x630/cdn.vox-cdn.com/uploads/chorus_asset/file/22883440/vpavic_210924_4766_0022_2.jpg",
            publishedAt = "2021-10-06T12:49:02Z",
            content = "You can also save on the Apple Pencil and AirPods Max If you buy something from a Verge link, Vox Media may earn a commission. See our ethics statement. The new iPad Mini is \$25 off today, the firs… [+4859 chars]"
        )
    )
}