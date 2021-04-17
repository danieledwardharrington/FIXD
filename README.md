# General Comments

The biggest comment I have is just slightly modifying the Post Fragment. I noticed the mock-up has an arrow in the top left to go back. I opted not to implement this since Androids have either a digital back button, physical button, 
or the back gesture and I felt the back arrow might be a bit redundant. Also, I used room to store the currently logged in user. If I were really fleshing out this app more, I'd use Room more extensively but didn't want to go 
overkill for the scope of this assessment.

# Libraries

* Kotlinx serialization - I went with this over the included Moshi because I'm more familiar with it and I wanted to reduce libraries as much as possible. Also, Kotlinx has multi-platform support so that was a long term consideration given the prompt.
* Pagination V3 - Infinite scrolling
* Room - Local storage
* sdp and ssp - These are some libraries that are friendlier to varying screen sizes rather than just using the standard dp and sp
* MaterialRatingBar - The one built into Android sucks

# Something I Couldn't Solve

So I kept having this issue where, after clicking logout from the PostFragment and being sent to the LoginFragment, I was able to navigate to the FeedFragment by simply pressing the back button. I'm really not sure why this is happening but 
I didn't want to go way over time trying to figure this issue out. 