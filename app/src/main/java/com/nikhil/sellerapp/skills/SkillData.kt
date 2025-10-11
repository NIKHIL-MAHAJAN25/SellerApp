package com.nikhil.sellerapp.skills

object SkillData {
    fun getSkillCategories(): List<SkillsCat> {
        return listOf(
            SkillsCat(
                categoryName = "Programming Languages",
                skills = listOf("Python", "Java", "JavaScript", "TypeScript", "Kotlin", "Swift", "C++", "C#", "Go", "Rust", "PHP", "Ruby", "SQL")
            ),
            SkillsCat(
                categoryName = "Frontend Development",
                skills = listOf("React", "Angular", "Vue.js", "HTML5", "CSS3", "SASS", "LESS", "Webpack", "Vite", "Next.js", "jQuery", "Tailwind CSS")
            ),
        SkillsCat(
                categoryName = "Backend Development",
                skills = listOf("Node.js", "Express.js", "Django", "Flask", "Ruby on Rails", "Spring Boot", "ASP.NET Core", "Laravel", "GraphQL", "REST APIs")
            ),
        SkillsCat(
                categoryName = "Mobile App Development",
                skills = listOf("Android (Kotlin)", "Android (Java)", "iOS (Swift)", "iOS (Objective-C)", "React Native", "Flutter", "Jetpack Compose", "SwiftUI")
            ),
        SkillsCat(
                categoryName = "Databases",
                skills = listOf("MySQL", "PostgreSQL", "Microsoft SQL Server", "MongoDB", "Redis", "Oracle Database", "Firebase Realtime Database", "Firestore", "SQLite")
            ),
        SkillsCat(
                categoryName = "Cloud & DevOps",
                skills = listOf("AWS (Amazon Web Services)", "Microsoft Azure", "GCP (Google Cloud Platform)", "Docker", "Kubernetes", "Jenkins", "Git", "Terraform", "CI/CD", "Nginx", "Apache")
            ),
        SkillsCat(
                categoryName = "Data Science & Machine Learning",
                skills = listOf("TensorFlow", "PyTorch", "Scikit-learn", "Pandas", "NumPy", "Jupyter Notebook", "Apache Spark", "R", "Machine Learning", "Data Analysis", "Data Visualization")
            ),
        SkillsCat(
                categoryName = "UI/UX Design",
                skills = listOf("Figma", "Sketch", "Adobe XD", "User Research", "Wireframing", "Prototyping", "Design Systems", "User Interface Design", "User Experience Design")
            ),
        SkillsCat(
                categoryName = "Project Management & Methodologies",
                skills = listOf("Agile", "Scrum", "Kanban", "JIRA", "Confluence", "Trello", "Asana", "Project Management", "Product Management")
            )
        )
    }
}