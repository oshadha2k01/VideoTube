<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="ISO-8859-1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>YouTube Clone - Video Hub</title>
    <!-- Font Awesome for Icons -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <!-- Google Fonts -->
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@300;400;500;700&display=swap" rel="stylesheet">
    <!-- Custom CSS -->
    <link rel="stylesheet" href="home.css">
</head>
<body>

    <!-- Header Section -->
    <header class="header">
        <div class="header-left">
            <i class="fas fa-bars menu-icon"></i>
            <a href="home.jsp" class="logo">
                <i class="fab fa-youtube"></i>
                <span>VideoHub</span>
            </a>
        </div>
        
        <div class="header-center">
            <div class="search-container">
                <input type="text" placeholder="Search">
            </div>
            <button class="search-btn">
                <i class="fas fa-search"></i>
            </button>
            <button class="mic-btn">
                <i class="fas fa-microphone"></i>
            </button>
        </div>
        
        <div class="header-right">
            <i class="fas fa-video" title="Create"></i>
            <i class="fas fa-th" title="Apps"></i>
            <i class="fas fa-bell" title="Notifications"></i>
            <div class="user-profile" title="User Account">
                <a href="useraccount.jsp" style="color: white; text-decoration: none;">U</a>
            </div>
            <a href="LogoutServlet" title="Logout" style="color: var(--text-secondary); margin-left: 10px; font-size: 18px;">
                <i class="fas fa-sign-out-alt"></i>
            </a>
        </div>
    </header>

    <!-- Sidebar Section -->
    <aside class="sidebar">
        <div class="sidebar-section">
            <a href="home.jsp" class="sidebar-item active">
                <i class="fas fa-home"></i>
                <span>Home</span>
            </a>
            <a href="#" class="sidebar-item">
                <i class="fab fa-youtube-square"></i>
                <span>Shorts</span>
            </a>
            <a href="#" class="sidebar-item">
                <i class="fas fa-layer-group"></i>
                <span>Subscriptions</span>
            </a>
        </div>
        
        <div class="sidebar-section">
            <a href="#" class="sidebar-item">
                <i class="fas fa-photo-video"></i>
                <span>Library</span>
            </a>
            <a href="#" class="sidebar-item">
                <i class="fas fa-history"></i>
                <span>History</span>
            </a>
            <a href="validatecontent.jsp" class="sidebar-item">
                <i class="fas fa-play-circle"></i>
                <span>Your videos</span>
            </a>
            <a href="#" class="sidebar-item">
                <i class="fas fa-clock"></i>
                <span>Watch later</span>
            </a>
            <a href="#" class="sidebar-item">
                <i class="fas fa-thumbs-up"></i>
                <span>Liked videos</span>
            </a>
        </div>

        <div class="sidebar-section">
            <p class="sidebar-title">Subscriptions</p>
            <a href="#" class="sidebar-item">
                <div class="channel-avatar" style="width: 24px; height: 24px; background-color: #4caf50;"></div>
                <span>Music Hub</span>
            </a>
            <a href="#" class="sidebar-item">
                <div class="channel-avatar" style="width: 24px; height: 24px; background-color: #2196f3;"></div>
                <span>Travel Vlogs</span>
            </a>
            <a href="#" class="sidebar-item">
                <div class="channel-avatar" style="width: 24px; height: 24px; background-color: #ff9800;"></div>
                <span>Foodie Guide</span>
            </a>
        </div>
    </aside>

    <!-- Main Content -->
    <main class="main-content">
        <!-- Categories Filter -->
        <div class="categories-bar">
            <div class="category-pill active">All</div>
            <div class="category-pill">Music</div>
            <div class="category-pill">Gaming</div>
            <div class="category-pill">Live</div>
            <div class="category-pill">Mixes</div>
            <div class="category-pill">Travel</div>
            <div class="category-pill">Food</div>
            <div class="category-pill">Sports</div>
            <div class="category-pill">Recently uploaded</div>
            <div class="category-pill">Watched</div>
            <div class="category-pill">New to you</div>
        </div>

        <!-- Video Grid -->
        <div class="video-grid">
            
            <!-- Video Card 1 -->
            <div class="video-card">
                <div class="thumbnail-container">
                    <img src="image1/Music.png" alt="Music Thumbnail">
                    <span class="video-duration">4:32</span>
                </div>
                <div class="video-info-container">
                    <div class="channel-avatar" style="background-color: #e91e63;"></div>
                    <div class="video-details">
                        <h3 class="video-title">Top 10 Hits of 2024 - Nonstop Music Mix</h3>
                        <p class="channel-name">Music Hub</p>
                        <p class="video-stats">1.2M views • 2 days ago</p>
                    </div>
                </div>
            </div>

            <!-- Video Card 2 -->
            <div class="video-card">
                <div class="thumbnail-container">
                    <img src="image1/travel.jpg" alt="Travel Thumbnail">
                    <span class="video-duration">12:15</span>
                </div>
                <div class="video-info-container">
                    <div class="channel-avatar" style="background-color: #2196f3;"></div>
                    <div class="video-details">
                        <h3 class="video-title">Exploring the Hidden Gems of Sri Lanka</h3>
                        <p class="channel-name">Travel Vlogs</p>
                        <p class="video-stats">850K views • 1 week ago</p>
                    </div>
                </div>
            </div>

            <!-- Video Card 3 -->
            <div class="video-card">
                <div class="thumbnail-container">
                    <img src="image1/foood.jpg" alt="Food Thumbnail">
                    <span class="video-duration">8:45</span>
                </div>
                <div class="video-info-container">
                    <div class="channel-avatar" style="background-color: #ff9800;"></div>
                    <div class="video-details">
                        <h3 class="video-title">Healthy Breakfast Recipes You Can Make in 5 Minutes</h3>
                        <p class="channel-name">Foodie Guide</p>
                        <p class="video-stats">2.5M views • 3 months ago</p>
                    </div>
                </div>
            </div>

            <!-- Video Card 4 -->
            <div class="video-card">
                <div class="thumbnail-container">
                    <img src="image1/sport.jpg" alt="Sport Thumbnail">
                    <span class="video-duration">15:20</span>
                </div>
                <div class="video-info-container">
                    <div class="channel-avatar" style="background-color: #4caf50;"></div>
                    <div class="video-details">
                        <h3 class="video-title">Epic Sport Moments: Highlights of the Season</h3>
                        <p class="channel-name">Sports World</p>
                        <p class="video-stats">5.1M views • 5 days ago</p>
                    </div>
                </div>
            </div>

            <!-- Video Card 5 -->
            <div class="video-card">
                <div class="thumbnail-container">
                    <img src="image1/bg2.jpg" alt="Nature Thumbnail">
                    <span class="video-duration">10:00</span>
                </div>
                <div class="video-info-container">
                    <div class="channel-avatar" style="background-color: #9c27b0;"></div>
                    <div class="video-details">
                        <h3 class="video-title">Relaxing Nature Sounds for Deep Sleep and Study</h3>
                        <p class="channel-name">Nature Hub</p>
                        <p class="video-stats">12M views • 1 year ago</p>
                    </div>
                </div>
            </div>

            <!-- Video Card 6 -->
            <div class="video-card">
                <div class="thumbnail-container">
                    <img src="image1/bg1.jpg" alt="Adventure Thumbnail">
                    <span class="video-duration">18:45</span>
                </div>
                <div class="video-info-container">
                    <div class="channel-avatar" style="background-color: #795548;"></div>
                    <div class="video-details">
                        <h3 class="video-title">How I Traveled Across Europe on a Budget</h3>
                        <p class="channel-name">Solo Traveler</p>
                        <p class="video-stats">430K views • 2 weeks ago</p>
                    </div>
                </div>
            </div>

        </div>
    </main>

    <script>
        // Simple script to handle category pill clicks
        const pills = document.querySelectorAll('.category-pill');
        pills.forEach(pill => {
            pill.addEventListener('click', () => {
                pills.forEach(p => p.classList.remove('active'));
                pill.classList.add('active');
            });
        });

        // Simple script to toggle sidebar (simulated)
        const menuIcon = document.querySelector('.menu-icon');
        const sidebar = document.querySelector('.sidebar');
        const mainContent = document.querySelector('.main-content');

        menuIcon.addEventListener('click', () => {
            // This is a simple toggle, in a real app we'd handle the 'mini-sidebar' state
            alert('Sidebar toggle functionality would go here!');
        });
    </script>

</body>
</html>